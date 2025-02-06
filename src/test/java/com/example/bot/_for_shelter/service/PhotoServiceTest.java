package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.model.Photo;
import com.example.bot._for_shelter.model.Report;
import com.example.bot._for_shelter.repository.PhotoRepository;
import com.example.bot._for_shelter.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PhotoServiceTest {

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private PhotoService photoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadPhoto() throws IOException {
        int reportId = 1;
        Report report = new Report();
        report.setId(reportId);

        when(multipartFile.getSize()).thenReturn(1024L);
        when(multipartFile.getContentType()).thenReturn("image/jpeg");
        when(multipartFile.getBytes()).thenReturn(new byte[]{1, 2, 3});
        when(reportRepository.findById((long) reportId)).thenReturn(Optional.of(report));

        Photo savedPhoto = new Photo();
        savedPhoto.setId(1L);
        when(photoRepository.save(any(Photo.class))).thenReturn(savedPhoto);

        int result = Math.toIntExact(photoService.uploadPhoto((long) reportId, multipartFile));

        assertEquals(1L, result);
        verify(photoRepository, times(1)).save(any(Photo.class));
    }

    @Test
    void testUploadPhotoThrowsExceptionWhenReportNotFound() {
        Long reportId = 1L;

        when(reportRepository.findById(reportId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> photoService.uploadPhoto(reportId, multipartFile));
    }

    @Test
    void testFindAvatar() {
        Long photoId = 1L;
        Photo photo = new Photo();
        photo.setId(photoId);

        when(photoRepository.findById(photoId)).thenReturn(Optional.of(photo));

        Photo result = photoService.findAvatar(photoId);

        assertNotNull(result);
        assertEquals(photoId, result.getId());
    }

    @Test
    void testFindAvatarThrowsExceptionWhenPhotoNotFound() {
        Long photoId = 1L;

        when(photoRepository.findById(photoId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> photoService.findAvatar(photoId));
    }
}

package com.denysenko.musicservice.services;

import com.denysenko.musicservice.Album;
import com.denysenko.musicservice.exceptions.RestServiceException;
import org.springframework.stereotype.Service;


@Service
public interface MusicService {
    Album getAlbum(String track, String singer) throws RestServiceException;
}

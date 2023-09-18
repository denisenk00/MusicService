package com.denysenko.musicservice.services;

import com.denysenko.musicservice.model.Album;
import com.denysenko.musicservice.exceptions.RemoteServiceException;

import java.util.Optional;


public interface MusicService {

    Optional<Album> findAlbum(String track, String singer) throws RemoteServiceException;

}

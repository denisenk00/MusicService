package com.denysenko.musicservice.services;

import com.denysenko.musicservice.forms.Response;
import com.denysenko.musicservice.exceptions.RestServiceException;
import org.springframework.stereotype.Service;


@Service
public interface MusicService {
    Response getInfoFromService() throws RestServiceException;
}

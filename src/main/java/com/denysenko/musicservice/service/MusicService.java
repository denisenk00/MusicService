package com.denysenko.musicservice.service;

import com.denysenko.musicservice.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public interface MusicService {
    Response getInfo();
}

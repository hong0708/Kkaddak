package com.example.kkaddak.api.service.impl;

import com.example.kkaddak.api.service.NFTService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class NFTServiceImpl implements NFTService {
    @Override
    public String generateCombination() {
        Random random = new Random();

        String body = Integer.toString(random.nextInt(9) + 1);
        String head = "," + (random.nextInt(18) + 1);
        String check = "," + (random.nextInt(3) + 1);
        String eyes = "," + (random.nextInt(11) + 1);
        String mouth = "," + (random.nextInt(11) + 1);
        String acc = "," + (random.nextInt(2) + 1);

        return body + head + check + eyes + mouth + acc;
    }
}

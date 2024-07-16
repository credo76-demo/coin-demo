package org.credo.labs.coindemo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CoindeskService {
    final static String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
}

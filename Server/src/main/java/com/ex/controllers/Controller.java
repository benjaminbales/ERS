package com.ex.controllers;

import org.json.simple.JSONObject;

public interface Controller {

    JSONObject doPost(String path, String body);
}

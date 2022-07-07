package com.example.sarafan.controllers;

import com.example.sarafan.exceptions.NotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {
    private final List<Map<String, String>> mapList = new ArrayList<>() {
        {
            add(new HashMap<>() {{
                put("id", "1");
                put("text", "First");
            }});
            add(new HashMap<>() {{
                put("id", "2");
                put("text", "Second");
            }});
            add(new HashMap<>() {{
                put("id", "3");
                put("text", "Third");
            }});
            add(new HashMap<>() {{
                put("id", "4");
                put("text", "Fourth");
            }});
        }
    };
    int counter = mapList.size();

    @GetMapping()
    public List<Map<String, String>> getMessageList() {
        return mapList;
    }

    @GetMapping("{id}")
    public Map<String, String> getIneMessage(@PathVariable String id) {
        return getMessage(id);
    }

    private Map<String, String> getMessage(String id) {
        return mapList
                .stream()
                .filter(mapList -> mapList.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping()
    public Map<String, String> createMessage(@RequestBody Map<String, String> message) {
        message.put("id", ++counter + "");
        mapList.add(message);
        return message;
    }

    @PutMapping("{id}")
    public Map<String, String> updateMessage(@PathVariable String id, @RequestBody Map<String, String> message) {
        System.out.println(id);
        Map<String, String> messageFromDB = getMessage(id);
        messageFromDB.putAll(message);
        messageFromDB.put("id", id);
        return messageFromDB;
    }

    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable String id) {
        Map<String, String> message = getMessage(id);
        mapList.remove(message);
    }
}

package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.*;
import com.example.demo.services.*;



@Controller
public class PlaylistController {
  @Autowired
  SongService songService;
  
  @Autowired
  PlaylistService playlistService;
  
  @GetMapping("/createPlaylist")
  public String createPlaylist(Model model) {
    List<Song> songList= songService.fetchAllSongs();
    model.addAttribute("songs",songList);
    return  "createPlaylist";
  }
  
  
  @PostMapping("/addPlaylist")
  public String addPlaylist(@ModelAttribute Playlist playlist) {
    //update playlist table
    playlistService.addPlaylists(playlist);
    
    //updating song table 
    List<Song> songList=playlist.getSongs();
    for(Song s:songList)
    {
      s.getPlaylist().add(playlist);
      //update song object in database
      songService.updateSong(s);
    }
    return "adminHome";
  }
  
  @GetMapping("/viewPlaylists")
  public String viewPlaylists(Model model) {
    List<Playlist> allPLaylists=playlistService.fetchAllPlaylists();
    model.addAttribute("allPLaylists",allPLaylists);
    
    return "displayPlaylists";
  }
  
}

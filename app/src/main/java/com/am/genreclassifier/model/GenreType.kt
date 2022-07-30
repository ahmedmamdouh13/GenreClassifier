package com.am.genreclassifier.model

enum class GenreType(s: String) {
   ROCK("rock"),
   CLASSICAL("classical"),
   DISCO("disco"),
   COUNTRY("country"),
   HIPHOP("hiphop"),
   BLUES("blues"),
   METAL("metal"),
   REGGAE("reggae"),
   POP("pop"),
   JAZZ("jazz"),
   LOADING("In-Progress"),
   IDLE("Idle");
   val displayableName = s
}
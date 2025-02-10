package com.example.mindspark.bookmark.data

import com.example.mindspark.bookmark.model.BookMarkModel

fun getAllBookMarks(): List<BookMarkModel> = listOf(
    BookMarkModel(
        title = "Graphic Design Basics",
        courseId = 1,
        status = "Bookmarked"
    ),
    BookMarkModel(
        title = "Advanced Graphic Design",
        courseId = 2,
        status = "Bookmarked"
    ),
    BookMarkModel(
        title = "Photoshop for Beginners",
        courseId = 3,
        status = "Bookmarked"
    ),
    BookMarkModel(
        title = "Illustrator Mastery",
        courseId = 4,
        status = "Bookmarked"
    ),
    BookMarkModel(
        title = "Master 3D Animation with Blender",
        courseId = 5,
        status = "Bookmarked"
    ),
    BookMarkModel(
        title = "3D Modeling for Beginners",
        courseId = 6,
        status = "Bookmarked"
    ),
    BookMarkModel(
        title = "Advanced 3D Sculpting",
        courseId = 7,
        status = "Bookmarked"
    ),
    BookMarkModel(
        title = "3D Printing Essentials",
        courseId = 8,
        status = "Bookmarked"
    ),
    BookMarkModel(
        title = "Full-Stack Web Development with React & Node",
        courseId = 9,
        status = "Bookmarked"
    ),
    BookMarkModel(
        title = "Front-End Development with Vue.js",
        courseId = 10,
        status = "Bookmarked"
    ),
    BookMarkModel(
        title = "Backend Development with Django",
        courseId = 11,
        status = "Bookmarked"
    )
)
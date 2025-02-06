package com.example.mindspark.bookmark.data

import com.example.mindspark.bookmark.model.BookMarkModel

fun getAllBookMarks(): List<BookMarkModel> = listOf(
    BookMarkModel(
        title = "Graphic Design Basics",
        course = "Web Designer",
        status = "Bookmarked"
    ),
    BookMarkModel(
        title = "Advanced Graphic Design",
        course = "Graphic Design",
        status = "Bookmarked"
    ),
    BookMarkModel(
        title = "Photoshop for Beginners",
        course = "Mobile Development",
        status = "Bookmarked"
    ),
    BookMarkModel(
        title = "Illustrator Mastery",
        course = "Programming",
        status = "Bookmarked"
    ),
    BookMarkModel(
        title = "3D Animation with Blender",
        course = "Software Engineering",
        status = "Bookmarked"
    ),
    BookMarkModel(
        title = "3D Modeling for Beginners",
        course = "Artificial Intelligence",
        status = "Bookmarked"
    ),
    BookMarkModel(
        title = "Advanced 3D Sculpting",
        course = "Network Security",
        status = "Bookmarked"
    ),
    BookMarkModel(
        title = "3D Printing Essentials",
        course = "Marketing",
        status = "Bookmarked"
    ),
    BookMarkModel(
        title = "Full-Stack Web Development with React & Node",
        course = "Web Development",
        status = "Bookmarked"
    ),
    BookMarkModel(
        title = "Front-End Development with Vue.js",
        course = "Data Analytics",
        status = "Bookmarked"
    ),
    BookMarkModel(
        title = "Backend Development with Django",
        course = "Cloud Technology",
        status = "Bookmarked"
    )
)
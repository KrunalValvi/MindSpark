package com.example.mindspark.courses.data

import com.example.mindspark.courses.model.CourseCategory
import com.example.mindspark.courses.model.CourseModel

object  CourseData {

    fun getAllCategories(): List<CourseCategory> = CourseCategory.values().toList()

    // Get popular courses
    fun getPopularCourses(): List<CourseModel> = listOf(
        CourseModel(
            category = "3D Design",
            title = "Master 3D Animation with Blender",
            price = "1500/-",
            rating = "4.8",
            students = "6500 Std",
            videos = "25",
            hours = "50",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "A complete guide to 3D animation using Blender. Learn modeling, texturing, " +
                    "rigging, and animation techniques to create stunning 3D visuals.",
            id = 1 // Jiya
        ),
        CourseModel(
            category = "Web Development",
            title = "Full-Stack Web Development with React & Node",
            price = "2000/-",
            rating = "4.9",
            students = "7100 Std",
            videos = "30",
            hours = "60",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "Master full-stack development with React and Node.js. This course covers front-end, " +
                    "back-end, databases, and deployment.",
            id = 2 // Aman
        ),
        CourseModel(
            category = "App Development",
            title = "Android App Development with Kotlin",
            price = "2500/-",
            rating = "4.8",
            students = "7300 Std",
            videos = "32",
            hours = "64",
            difficultyLevel = "Intermediate",
            language = "English, Hindi",
            certification = "Yes",
            about = "Build Android apps using Kotlin and Jetpack Compose. This course covers UI design, " +
                    "APIs, databases, and deployment.",
            id = 3 // Rahul
        ),
        CourseModel(
            category = "UI/UX Design",
            title = "UI/UX Design Fundamentals",
            price = "1200/-",
            rating = "4.6",
            students = "4800 Std",
            videos = "20",
            hours = "40",
            difficultyLevel = "Beginner",
            language = "English",
            certification = "Yes",
            about = "Learn the principles of UI/UX design, including user research, wireframing, and prototyping.",
            id = 4 // Manev
        ),
        CourseModel(
            category = "Game Development",
            title = "Game Development with Unity",
            price = "1800/-",
            rating = "4.7",
            students = "5000 Std",
            videos = "22",
            hours = "45",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Create engaging games using Unity. This course covers game mechanics, physics, and design.",
            id = 5 // Raghav
        ),
        CourseModel(
            category = "Data Science",
            title = "Data Science with Python",
            price = "2200/-",
            rating = "4.9",
            students = "8600 Std",
            videos = "28",
            hours = "56",
            difficultyLevel = "Advanced",
            language = "English, Spanish",
            certification = "Yes",
            about = "Learn data science with Python, covering data analysis, machine learning, " +
                    "visualization, and predictive modeling.",
            id = 6 // Mihir
        ),
        CourseModel(
            category = "AI & Machine Learning",
            title = "Introduction to AI and Machine Learning",
            price = "2000/-",
            rating = "4.8",
            students = "7000 Std",
            videos = "30",
            hours = "60",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Explore the fundamentals of AI and machine learning, including algorithms and applications.",
            id = 7 // Mohit
        ),
        CourseModel(
            category = "Digital Marketing",
            title = "Digital Marketing Strategies",
            price = "1500/-",
            rating = "4.5",
            students = "4000 Std",
            videos = "18",
            hours = "36",
            difficultyLevel = "Beginner",
            language = "English",
            certification = "Yes",
            about = "Learn effective digital marketing strategies, including SEO, social media, and content marketing.",
            id = 8 // Sneha
        ),
        CourseModel(
            category = "Graphic Design",
            title = "Graphic Design Basics",
            price = "1000/-",
            rating = "4.4",
            students = "3000 Std",
            videos = "15",
            hours = "30",
            difficultyLevel = "Beginner",
            language = "English",
            certification = "Yes",
            about = "Understand the basics of graphic design, including color theory, typography, and layout.",
            id = 9 // Pooja
        ),
        CourseModel(
            category = "Cloud Computing",
            title = "Cloud Computing Essentials",
            price = "1800/-",
            rating = "4.6",
            students = "5500 Std",
            videos = "20",
            hours = "40",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Learn the fundamentals of cloud computing, including services, deployment models, and security.",
            id = 10 // Rajesh
        ),
        CourseModel(
            category = "Cybersecurity",
            title = "Cybersecurity Fundamentals",
            price = "2000/-",
            rating = "4.7",
            students = "6000 Std",
            videos = "25",
            hours = "50",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Understand the basics of cybersecurity, including threats, vulnerabilities, and protection strategies.",
            id = 11 // Neha
        ),
        CourseModel(
            category = "Blockchain",
            title = "Blockchain Technology and Applications",
            price = "2200/-",
            rating = "4.8",
            students = "7000 Std",
            videos = "30",
            hours = "60",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "Explore blockchain technology, its applications, and how it is transforming industries.",
            id = 12 // Siddharth
        ),
        CourseModel(
            category = "Product Management",
            title = "Product Management Essentials",
            price = "1500/-",
            rating = "4.5",
            students = "4000 Std",
            videos = "18",
            hours = "36",
            difficultyLevel = "Beginner",
            language = "English",
            certification = "Yes",
            about = "Learn the fundamentals of product management, including product lifecycle, market research, and strategy.",
            id = 13 // Kriti
        ),
        CourseModel(
            category = "DevOps",
            title = "DevOps Practices and Tools",
            price = "2000/-",
            rating = "4.6",
            students = "5000 Std",
            videos = "22",
            hours = "45",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Understand DevOps practices and tools to improve collaboration and productivity in software development.",
            id = 14 // Arjun
        )
    )

}
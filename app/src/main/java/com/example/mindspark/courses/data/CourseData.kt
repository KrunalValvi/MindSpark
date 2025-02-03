package com.example.mindspark.courses.data

import com.example.mindspark.R
import com.example.mindspark.courses.model.CourseCategory
import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.courses.model.FeatureModel

object CourseData {

    fun getAllCategories(): List<CourseCategory> = CourseCategory.entries

    // Get popular courses
    fun getPopularCourses(): List<CourseModel> = listOf(
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
            id = 1,
            features = listOf(
                FeatureModel("15 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
//        CourseModel(
//            category = "Graphic Design",
//            title = "Advanced Graphic Design Techniques",
//            price = "1500/-",
//            rating = "4.7",
//            students = "4500 Std",
//            videos = "25",
//            hours = "50",
//            difficultyLevel = "Advanced",
//            language = "English",
//            certification = "Yes",
//            about = "Explore advanced techniques in graphic design, including digital illustration and branding.",
//            mentorId = 2,
//            features = listOf(
//                FeatureModel("25 Lessons", R.drawable.ic_lessons),
//                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
//                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
//                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
//            )
//        ),
//        CourseModel(
//            category = "Graphic Design",
//            title = "Photoshop for Beginners",
//            price = "1200/-",
//            rating = "4.5",
//            students = "3800 Std",
//            videos = "20",
//            hours = "40",
//            difficultyLevel = "Beginner",
//            language = "English",
//            certification = "Yes",
//            about = "Learn the basics of Photoshop, including photo editing and graphic creation.",
//            mentorId = 3,
//            features = listOf(
//                FeatureModel("20 Lessons", R.drawable.ic_lessons),
//                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
//                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
//                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
//            )
//        ),
//        CourseModel(
//            category = "Graphic Design",
//            title = "Illustrator Mastery",
//            price = "1800/-",
//            rating = "4.8",
//            students = "5000 Std",
//            videos = "30",
//            hours = "60",
//            difficultyLevel = "Advanced",
//            language = "English",
//            certification = "Yes",
//            about = "Master Adobe Illustrator for creating stunning vector graphics.",
//            mentorId = 4,
//            features = listOf(
//                FeatureModel("30 Lessons", R.drawable.ic_lessons),
//                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
//                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
//                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
//            )
//        ),
//        // 3D Design
//        CourseModel(
//            category = "3D Design",
//            title = "Master 3D Animation with Blender",
//            price = "1500/-",
//            rating = "4.8",
//            students = "6500 Std",
//            videos = "25",
//            hours = "50",
//            difficultyLevel = "Advanced",
//            language = "English",
//            certification = "Yes",
//            about = "A complete guide to 3D animation using Blender. Learn modeling, texturing, rigging, and animation techniques to create stunning 3D visuals.",
//            mentorId = 5,
//            features = listOf(
//                FeatureModel("25 Lessons", R.drawable.ic_lessons),
//                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
//                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
//                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
//                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access),
//                FeatureModel("100 Quizzes", R.drawable.ic_quizzes)
//            )
//        ),
//        CourseModel(
//            category = "3D Design",
//            title = "3D Modeling for Beginners",
//            price = "1300/-",
//            rating = "4.6",
//            students = "4200 Std",
//            videos = "20",
//            hours = "40",
//            difficultyLevel = "Beginner",
//            language = "English",
//            certification = "Yes",
//            about = "Learn the basics of 3D modeling using various software tools.",
//            mentorId = 6,
//            features = listOf(
//                FeatureModel("20 Lessons", R.drawable.ic_lessons),
//                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
//                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
//                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
//            )
//        ),
//        CourseModel(
//            category = "3D Design",
//            title = "Advanced 3D Sculpting",
//            price = "1700/-",
//            rating = "4.7",
//            students = "5000 Std",
//            videos = "28",
//            hours = "55",
//            difficultyLevel = "Advanced",
//            language = "English",
//            certification = "Yes",
//            about = "Master the art of 3D sculpting with advanced techniques and tools.",
//            mentorId = 7,
//            features = listOf(
//                FeatureModel("28 Lessons", R.drawable.ic_lessons),
//                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
//                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
//                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
//            )
//        ),
//        CourseModel(
//            category = "3D Design",
//            title = "3D Printing Essentials",
//            price = "1400/-",
//            rating = "4.5",
//            students = "3500 Std",
//            videos = "22",
//            hours = "45",
//            difficultyLevel = "Intermediate",
//            language = "English",
//            certification = "Yes",
//            about = "Learn the essentials of 3D printing, from modeling to printing.",
//            mentorId = 8,
//            features = listOf(
//                FeatureModel("22 Lessons", R.drawable.ic_lessons),
//                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
//                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
//                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
//            )
//        ),
//        // Web Development
//        CourseModel(
//            category = "Web Development",
//            title = "Full-Stack Web Development with React & Node",
//            price = "2000/-",
//            rating = "4.9",
//            students = "7100 Std",
//            videos = "30",
//            hours = "60",
//            difficultyLevel = "Advanced",
//            language = "English",
//            certification = "Yes",
//            about = "Master full-stack development with React and Node.js. This course covers front-end, back-end, databases, and deployment.",
//            mentorId = 9,
//            features = listOf(
//                FeatureModel("30 Lessons", R.drawable.ic_lessons),
//                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
//                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
//                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
//                FeatureModel("Audio Book", R.drawable.ic_audiobook),
//                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
//            )
//        ),
//        CourseModel(
//            category = "Web Development",
//            title = "Front-End Development with Vue.js",
//            price = "1800/-",
//            rating = "4.8",
//            students = "6200 Std",
//            videos = "25",
//            hours = "50",
//            difficultyLevel = "Intermediate",
//            language = "English",
//            certification = "Yes",
//            about = "Learn front-end development using Vue.js, covering components, state management, and routing.",
//            mentorId = 10,
//            features = listOf(
//                FeatureModel("25 Lessons", R.drawable.ic_lessons),
//                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
//                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
//                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
//                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
//            )
//        ),
//        CourseModel(
//            category = "Web Development",
//            title = "Backend Development with Django",
//            price = "1700/-",
//            rating = "4.7",
//            students = "5800 Std",
//            videos = "24",
//            hours = "48",
//            difficultyLevel = "Intermediate",
//            language = "English",
//            certification = "Yes",
//            about = "Master backend development using Django, covering models, views, and deployment.",
//            mentorId = 11,
//            features = listOf(
//                FeatureModel("24 Lessons", R.drawable.ic_lessons),
//                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
//                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
//                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
//            )
//        ),
//        CourseModel(
//            category = "Web Development",
//            title = "Responsive Web Design",
//            price = "1500/-",
//            rating = "4.6",
//            students = "5400 Std",
//            videos = "20",
//            hours = "40",
//            difficultyLevel = "Beginner",
//            language = "English",
//            certification = "Yes",
//            about = "Learn the principles of responsive web design, including media queries and flexible layouts.",
//            mentorId = 12,
//            features = listOf(
//                FeatureModel("20 Lessons", R.drawable.ic_lessons),
//                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
//                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
//                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
//            )
//        ),
//        // Digital Marketing
//        CourseModel(
//            category = "Digital Marketing",
//            title = "Digital Marketing Strategies",
//            price = "1500/-",
//            rating = "4.5",
//            students = "4000 Std",
//            videos = "18",
//            hours = "36",
//            difficultyLevel = "Beginner",
//            language = "English",
//            certification = "Yes",
//            about = "Learn effective digital marketing strategies, including SEO, social media, and content marketing.",
//            mentorId = 13,
//            features = listOf(
//                FeatureModel("18 Lessons", R.drawable.ic_lessons),
//                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
//                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
//                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
//                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
//            )
//        ),
//        CourseModel(
//            category = "Digital Marketing",
//            title = "Social Media Marketing",
//            price = "1400/-",
//            rating = "4.6",
//            students = "4200 Std",
//            videos = "20",
//            hours = "40",
//            difficultyLevel = "Intermediate",
//            language = "English",
//            certification = "Yes",
//            about = "Master social media marketing strategies to grow your brand and engage your audience.",
//            mentorId = 14,
//            features = listOf(
//                FeatureModel("20 Lessons", R.drawable.ic_lessons),
//                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
//                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
//                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
//                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
//            )
//        ),
//        CourseModel(
//            category = "Digital Marketing",
//            title = "Content Marketing Mastery",
//            price = "1600/-",
//            rating = "4.7",
//            students = "4500 Std",
//            videos = "22",
//            hours = "44",
//            difficultyLevel = "Advanced",
//            language = "English",
//            certification = "Yes",
//            about = "Learn advanced content marketing strategies to create and distribute valuable content.",
//            mentorId = 14,
//            features = listOf(
//                FeatureModel("22 Lessons", R.drawable.ic_lessons),
//                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
//                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
//                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
//                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
//            )
//        )
    )


    // Add this function to CourseData.kt
    fun getCourseById(courseId: Int): CourseModel? {
        return getPopularCourses().find { it.id == courseId }
    }


    // Get courses by mentor ID
    fun getCoursesByMentorId(mentorId: Int): List<CourseModel> {
        return getPopularCourses().filter { it.id == mentorId }
    }
}
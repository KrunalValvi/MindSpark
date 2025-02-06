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
            id = 1,
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
            mentorIds = listOf(4),
            imageRes = R.drawable.course_graphic_design_basics,
            features = listOf(
                FeatureModel("15 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        CourseModel(
            id = 2,
            category = "Graphic Design",
            title = "Advanced Graphic Design Techniques",
            price = "1500/-",
            rating = "4.7",
            students = "4500 Std",
            videos = "25",
            hours = "50",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "Explore advanced techniques in graphic design, including digital illustration and branding.",
            mentorIds = listOf(4),
            imageRes = R.drawable.course_advanced_graphic_design,
            features = listOf(
                FeatureModel("25 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        CourseModel(
            id = 3,
            category = "Graphic Design",
            title = "Photoshop for Beginners",
            price = "1200/-",
            rating = "4.5",
            students = "3800 Std",
            videos = "20",
            hours = "40",
            difficultyLevel = "Beginner",
            language = "English",
            certification = "Yes",
            about = "Learn the basics of Photoshop, including photo editing and graphic creation.",
            mentorIds = listOf(3),
            imageRes = R.drawable.course_photoshop_for_beginners,
            features = listOf(
                FeatureModel("20 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        CourseModel(
            id = 4,
            category = "Graphic Design",
            title = "Illustrator Mastery",
            price = "1800/-",
            rating = "4.8",
            students = "5000 Std",
            videos = "30",
            hours = "60",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "Master Adobe Illustrator for creating stunning vector graphics.",
            mentorIds = listOf(4),
            imageRes = R.drawable.course_illustrator_mastery,
            features = listOf(
                FeatureModel("30 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        CourseModel(
            id = 5,
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
            about = "A complete guide to 3D animation using Blender. Learn modeling, texturing, rigging, and animation techniques to create stunning 3D visuals.",
            mentorIds = listOf(1, 5),
            imageRes = R.drawable.course_3d_animation_blender,
            features = listOf(
                FeatureModel("25 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access),
                FeatureModel("100 Quizzes", R.drawable.ic_quizzes)
            )
        ),
        CourseModel(
            id = 6,
            category = "3D Design",
            title = "3D Modeling for Beginners",
            price = "1300/-",
            rating = "4.6",
            students = "4200 Std",
            videos = "20",
            hours = "40",
            difficultyLevel = "Beginner",
            language = "English",
            certification = "Yes",
            about = "Learn the basics of 3D modeling using various software tools.",
            mentorIds = listOf(1, 6),
            imageRes = R.drawable.course_3d_modeling_beginners,
            features = listOf(
                FeatureModel("20 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        CourseModel(
            id = 7,
            category = "3D Design",
            title = "Advanced 3D Sculpting",
            price = "1700/-",
            rating = "4.7",
            students = "5000 Std",
            videos = "28",
            hours = "55",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "Master the art of 3D sculpting with advanced techniques and tools.",
            mentorIds = listOf(1, 3, 7),
            imageRes = R.drawable.course_advanced_3d_sculpting,
            features = listOf(
                FeatureModel("28 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        CourseModel(
            id = 8,
            category = "3D Design",
            title = "3D Printing Essentials",
            price = "1400/-",
            rating = "4.5",
            students = "3500 Std",
            videos = "22",
            hours = "45",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Learn the essentials of 3D printing, from modeling to printing.",
            mentorIds = listOf(1, 5, 8),
            imageRes = R.drawable.course_3d_printing_essentials,
            features = listOf(
                FeatureModel("22 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        CourseModel(
            id = 9,
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
            about = "Master full-stack development with React and Node.js. This course covers front-end, back-end, databases, and deployment.",
            mentorIds = listOf(2, 9),
            imageRes = R.drawable.course_full_stack_react_node,
            features = listOf(
                FeatureModel("30 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Audio Book", R.drawable.ic_audiobook),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            id = 10,
            category = "Web Development",
            title = "Front-End Development with Vue.js",
            price = "1800/-",
            rating = "4.8",
            students = "6200 Std",
            videos = "25",
            hours = "50",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Learn front-end development using Vue.js, covering components, state management, and routing.",
            mentorIds = listOf(2, 10),
            imageRes = R.drawable.course_front_end_vue,
            features = listOf(
                FeatureModel("25 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            id = 11,
            category = "Web Development",
            title = "Backend Development with Django",
            price = "1700/-",
            rating = "4.7",
            students = "5800 Std",
            videos = "24",
            hours = "48",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Master backend development using Django, covering models, views, and deployment.",
            mentorIds = listOf(11),
            imageRes = R.drawable.course_backend_django,
            features = listOf(
                FeatureModel("24 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        CourseModel(
            id = 12,
            category = "Web Development",
            title = "Responsive Web Design",
            price = "1500/-",
            rating = "4.6",
            students = "5400 Std",
            videos = "20",
            hours = "40",
            difficultyLevel = "Beginner",
            language = "English",
            certification = "Yes",
            about = "Learn the principles of responsive web design, including media queries and flexible layouts.",
            mentorIds = listOf(12),
            imageRes = R.drawable.course_responsive_web_design,
            features = listOf(
                FeatureModel("20 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        CourseModel(
            id = 13,
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
            mentorIds = listOf(13),
            imageRes = R.drawable.course_digital_marketing_strategies,
            features = listOf(
                FeatureModel("18 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            id = 14,
            category = "Digital Marketing",
            title = "Social Media Marketing",
            price = "1400/-",
            rating = "4.6",
            students = "4200 Std",
            videos = "20",
            hours = "40",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Master social media marketing strategies to grow your brand and engage your audience.",
            mentorIds = listOf(14),
            imageRes = R.drawable.course_social_media_marketing,
            features = listOf(
                FeatureModel("20 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            id = 15,
            category = "Digital Marketing",
            title = "Content Marketing Mastery",
            price = "1600/-",
            rating = "4.7",
            students = "4500 Std",
            videos = "22",
            hours = "44",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "Learn advanced content marketing strategies to create and distribute valuable content.",
            mentorIds = listOf(14),
            imageRes = R.drawable.course_content_marketing_mastery,
            features = listOf(
                FeatureModel("22 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        )
    )

    fun getCourseById(courseId: Int): CourseModel? {
        return getPopularCourses().find { it.id == courseId }
    }

    fun getCoursesByMentorId(mentorId: Int): List<CourseModel> {
        return getPopularCourses().filter { it.mentorIds.contains(mentorId) }
    }
}
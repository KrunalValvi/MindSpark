package com.example.mindspark.courses.data

import com.example.mindspark.R
import com.example.mindspark.courses.model.CourseCategory

import com.example.mindspark.courses.model.CourseModel
import com.example.mindspark.courses.model.FeatureModel

object CourseData {

    fun getAllCategories(): List<CourseCategory> = CourseCategory.values().toList()

    // Get popular courses
    fun getPopularCourses(): List<CourseModel> = listOf(
        // Graphic Design
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
            mentorId = 1,
            features = listOf(
                FeatureModel("15 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        CourseModel(
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
            id = 2,
            features = listOf(
                FeatureModel("25 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        CourseModel(
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
            id = 3,
            features = listOf(
                FeatureModel("20 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        CourseModel(
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
            id = 4,
            features = listOf(
                FeatureModel("30 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        // 3D Design
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
            about = "A complete guide to 3D animation using Blender. Learn modeling, texturing, rigging, and animation techniques to create stunning 3D visuals.",
            id = 5,
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
            id = 6,
            features = listOf(
                FeatureModel("20 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        CourseModel(
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
            id = 7,
            features = listOf(
                FeatureModel("28 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        CourseModel(
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
            id = 8,
            features = listOf(
                FeatureModel("22 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        // Web Development
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
            about = "Master full-stack development with React and Node.js. This course covers front-end, back-end, databases, and deployment.",
            id = 9,
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
            id = 10,
            features = listOf(
                FeatureModel("25 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
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
            id = 11,
            features = listOf(
                FeatureModel("24 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        CourseModel(
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
            id = 12,
            features = listOf(
                FeatureModel("20 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        // Digital Marketing
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
            id = 13,
            features = listOf(
                FeatureModel("18 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
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
            id = 14,
            features = listOf(
                FeatureModel("20 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
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
            id = 15,
            features = listOf(
                FeatureModel("22 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        // App Development
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
            about = "Build Android apps using Kotlin and Jetpack Compose. This course covers UI design, APIs, databases, and deployment.",
            id = 16,
            features = listOf(
                FeatureModel("32 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "App Development",
            title = "iOS App Development with Swift",
            price = "2700/-",
            rating = "4.9",
            students = "8000 Std",
            videos = "35",
            hours = "70",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "Master iOS app development using Swift, covering UI design, APIs, and deployment.",
            id = 17,
            features = listOf(
                FeatureModel("35 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "App Development",
            title = "Cross-Platform App Development with Flutter",
            price = "2400/-",
            rating = "4.7",
            students = "6700 Std",
            videos = "30",
            hours = "60",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Learn to build cross-platform apps using Flutter. This course covers UI design, state management, and deployment.",
            id = 18,
            features = listOf(
                FeatureModel("30 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "App Development",
            title = "React Native for Mobile Development",
            price = "2300/-",
            rating = "4.6",
            students = "6400 Std",
            videos = "28",
            hours = "56",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Build mobile apps using React Native. This course covers components, state management, and deployment.",
            id = 19,
            features = listOf(
                FeatureModel("28 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        // UI/UX Design
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
            id = 20,
            features = listOf(
                FeatureModel("20 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        CourseModel(
            category = "UI/UX Design",
            title = "Advanced UX Design",
            price = "1600/-",
            rating = "4.7",
            students = "5000 Std",
            videos = "25",
            hours = "50",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "Master advanced UX design techniques, including usability testing and user research.",
            id = 21,
            features = listOf(
                FeatureModel("25 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        CourseModel(
            category = "UI/UX Design",
            title = "UI Design with Figma",
            price = "1400/-",
            rating = "4.5",
            students = "4600 Std",
            videos = "22",
            hours = "44",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Learn UI design using Figma, covering prototyping, components, and design systems.",
            id = 22,
            features = listOf(
                FeatureModel("22 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        // Game Development
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
            id = 23,
            features = listOf(
                FeatureModel("22 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access),
                FeatureModel("100 Quizzes", R.drawable.ic_quizzes)
            )
        ),
        CourseModel(
            category = "Game Development",
            title = "Advanced Game Development with Unreal Engine",
            price = "2000/-",
            rating = "4.8",
            students = "6000 Std",
            videos = "28",
            hours = "56",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "Master advanced game development techniques using Unreal Engine, covering physics, AI, and networking.",
            id = 24,
            features = listOf(
                FeatureModel("28 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "Game Development",
            title = "2D Game Development with Godot",
            price = "1500/-",
            rating = "4.6",
            students = "4500 Std",
            videos = "20",
            hours = "40",
            difficultyLevel = "Beginner",
            language = "English",
            certification = "Yes",
            about = "Learn the basics of 2D game development using the Godot engine.",
            id = 25,
            features = listOf(
                FeatureModel("20 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate)
            )
        ),
        CourseModel(
            category = "Game Development",
            title = "Mobile Game Development with Unity",
            price = "1700/-",
            rating = "4.7",
            students = "4800 Std",
            videos = "24",
            hours = "48",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Create mobile games using Unity. This course covers touch controls, optimization, and deployment.",
            id = 26,
            features = listOf(
                FeatureModel("24 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        // Data Science
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
            about = "Learn data science with Python, covering data analysis, machine learning, visualization, and predictive modeling.",
            id = 27,
            features = listOf(
                FeatureModel("28 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Audio Book", R.drawable.ic_audiobook),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "Data Science",
            title = "Machine Learning with Python",
            price = "2300/-",
            rating = "4.8",
            students = "7800 Std",
            videos = "30",
            hours = "60",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "Master machine learning with Python, covering algorithms, data preprocessing, and model evaluation.",
            id = 28,
            features = listOf(
                FeatureModel("30 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "Data Science",
            title = "Data Visualization with R",
            price = "2000/-",
            rating = "4.7",
            students = "6500 Std",
            videos = "25",
            hours = "50",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Learn data visualization techniques using R, covering ggplot2, Shiny, and interactive dashboards.",
            id = 29,
            features = listOf(
                FeatureModel("25 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "Data Science",
            title = "Big Data Analytics with Hadoop",
            price = "2500/-",
            rating = "4.8",
            students = "7000 Std",
            videos = "32",
            hours = "64",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "Master big data analytics using Hadoop, covering HDFS, MapReduce, and Spark.",
            id = 30,
            features = listOf(
                FeatureModel("32 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        // AI & Machine Learning
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
            id = 31,
            features = listOf(
                FeatureModel("30 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "AI & Machine Learning",
            title = "Deep Learning with TensorFlow",
            price = "2500/-",
            rating = "4.9",
            students = "8000 Std",
            videos = "35",
            hours = "70",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "Master deep learning techniques using TensorFlow, covering neural networks, CNNs, and RNNs.",
            id = 32,
            features = listOf(
                FeatureModel("35 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "AI & Machine Learning",
            title = "Natural Language Processing with Python",
            price = "2200/-",
            rating = "4.7",
            students = "7200 Std",
            videos = "28",
            hours = "56",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "Learn natural language processing techniques using Python, covering text preprocessing, word embeddings, and sequence models.",
            id = 33,
            features = listOf(
                FeatureModel("28 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "AI & Machine Learning",
            title = "Reinforcement Learning with Python",
            price = "2400/-",
            rating = "4.8",
            students = "7500 Std",
            videos = "30",
            hours = "60",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "Master reinforcement learning techniques using Python, covering Q-learning, deep Q-networks, and policy gradients.",
            id = 34,
            features = listOf(
                FeatureModel("30 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        // Cloud Computing
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
            id = 35,
            features = listOf(
                FeatureModel("20 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "Cloud Computing",
            title = "AWS Certified Solutions Architect",
            price = "2000/-",
            rating = "4.8",
            students = "6000 Std",
            videos = "25",
            hours = "50",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "Prepare for the AWS Certified Solutions Architect exam, covering AWS services, architecture, and best practices.",
            id = 36,
            features = listOf(
                FeatureModel("25 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "Cloud Computing",
            title = "Google Cloud Platform Fundamentals",
            price = "1900/-",
            rating = "4.7",
            students = "5700 Std",
            videos = "24",
            hours = "48",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Learn the fundamentals of Google Cloud Platform, covering services, deployment, and security.",
            id = 37,
            features = listOf(
                FeatureModel("24 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "Cloud Computing",
            title = "Azure Fundamentals",
            price = "1800/-",
            rating = "4.6",
            students = "5400 Std",
            videos = "20",
            hours = "40",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Learn the fundamentals of Microsoft Azure, covering services, deployment, and security.",
            id = 38,
            features = listOf(
                FeatureModel("20 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        // Cybersecurity
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
            id = 39,
            features = listOf(
                FeatureModel("25 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Audio Book", R.drawable.ic_audiobook),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "Cybersecurity",
            title = "Network Security",
            price = "2100/-",
            rating = "4.8",
            students = "6200 Std",
            videos = "28",
            hours = "56",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "Master network security techniques, covering firewalls, intrusion detection, and VPNs.",
            id = 40,
            features = listOf(
                FeatureModel("28 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "Cybersecurity",
            title = "Ethical Hacking",
            price = "2300/-",
            rating = "4.9",
            students = "7000 Std",
            videos = "30",
            hours = "60",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "Learn ethical hacking techniques to protect systems and networks from attacks.",
            id = 41,
            features = listOf(
                FeatureModel("30 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "Cybersecurity",
            title = "Cybersecurity for Beginners",
            price = "1800/-",
            rating = "4.6",
            students = "5400 Std",
            videos = "20",
            hours = "40",
            difficultyLevel = "Beginner",
            language = "English",
            certification = "Yes",
            about = "Learn the basics of cybersecurity, including how to protect yourself online and secure your devices.",
            id = 42,
            features = listOf(
                FeatureModel("20 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        // Blockchain
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
            id = 43,
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
            category = "Blockchain",
            title = "Smart Contract Development with Solidity",
            price = "2400/-",
            rating = "4.9",
            students = "7500 Std",
            videos = "32",
            hours = "64",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "Master smart contract development using Solidity, covering Ethereum, DApps, and security.",
            id = 44,
            features = listOf(
                FeatureModel("32 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "Blockchain",
            title = "Blockchain Development with Hyperledger Fabric",
            price = "2100/-",
            rating = "4.7",
            students = "6700 Std",
            videos = "28",
            hours = "56",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Learn blockchain development using Hyperledger Fabric, covering architecture, chaincode, and security.",
            id = 45,
            features = listOf(
                FeatureModel("28 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "Blockchain",
            title = "Introduction to Blockchain",
            price = "1800/-",
            rating = "4.5",
            students = "5400 Std",
            videos = "20",
            hours = "40",
            difficultyLevel = "Beginner",
            language = "English",
            certification = "Yes",
            about = "Explore the basics of blockchain technology, including its history, applications, and future potential.",
            id = 46,
            features = listOf(
                FeatureModel("20 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        // Product Management
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
            id = 47,
            features = listOf(
                FeatureModel("18 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Beginner Level", R.drawable.ic_beginner_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "Product Management",
            title = "Advanced Product Management",
            price = "2000/-",
            rating = "4.8",
            students = "6000 Std",
            videos = "25",
            hours = "50",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "Master advanced product management techniques, including market analysis, user research, and roadmap planning.",
            id = 48,
            features = listOf(
                FeatureModel("25 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "Product Management",
            title = "Agile Product Management",
            price = "1800/-",
            rating = "4.6",
            students = "5000 Std",
            videos = "22",
            hours = "44",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Learn product management in an agile environment, including Scrum, Kanban, and iterative development.",
            id = 49,
            features = listOf(
                FeatureModel("22 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "Product Management",
            title = "Product Analytics and Metrics",
            price = "1900/-",
            rating = "4.7",
            students = "5300 Std",
            videos = "24",
            hours = "48",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Understand the importance of analytics and metrics in product management, including user behavior, A/B testing, and KPI tracking.",
            id = 50,
            features = listOf(
                FeatureModel("24 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        // DevOps
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
            id = 51,
            features = listOf(
                FeatureModel("22 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "DevOps",
            title = "CI/CD with Jenkins",
            price = "2100/-",
            rating = "4.7",
            students = "5500 Std",
            videos = "24",
            hours = "48",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Learn continuous integration and continuous deployment using Jenkins, covering pipeline creation, automation, and best practices.",
            id = 52,
            features = listOf(
                FeatureModel("24 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "DevOps",
            title = "Infrastructure as Code with Terraform",
            price = "2300/-",
            rating = "4.8",
            students = "6000 Std",
            videos = "28",
            hours = "56",
            difficultyLevel = "Advanced",
            language = "English",
            certification = "Yes",
            about = "Master infrastructure as code using Terraform, covering provisioning, configuration management, and best practices.",
            id = 53,
            features = listOf(
                FeatureModel("28 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Advanced Level", R.drawable.ic_advanced_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        ),
        CourseModel(
            category = "DevOps",
            title = "Monitoring and Logging with Prometheus and Grafana",
            price = "2200/-",
            rating = "4.7",
            students = "5800 Std",
            videos = "26",
            hours = "52",
            difficultyLevel = "Intermediate",
            language = "English",
            certification = "Yes",
            about = "Learn monitoring and logging techniques using Prometheus and Grafana, covering metrics collection, visualization, and alerting.",
            id = 54,
            features = listOf(
                FeatureModel("26 Lessons", R.drawable.ic_lessons),
                FeatureModel("Access Mobile, Desktop & TV", R.drawable.ic_access_devices),
                FeatureModel("Intermediate Level", R.drawable.ic_intermediate_level),
                FeatureModel("Certificate of Completion", R.drawable.ic_certificate),
                FeatureModel("Lifetime Access", R.drawable.ic_lifetime_access)
            )
        )
    )
}

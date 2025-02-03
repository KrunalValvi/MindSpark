package com.example.mindspark.courses.data

import com.example.mindspark.courses.model.MentorCourseModel
import com.example.mindspark.courses.model.MentorModel
import com.example.mindspark.courses.model.ReviewModel

object MentorData {

    // Get top mentors
    fun getTopMentors(): List<MentorModel> {
        val mentors = listOf(
            MentorModel(
                id = 1,
                name = "Jiya",
                profession = "3D Design",
                courses = 4,
                students = 1500,
                ratings = 423,
                coursesList = listOf(
                    MentorCourseModel(
                        id = 5, // Maps to "Master 3D Animation with Blender"
                        title = "Master 3D Animation with Blender",
                        level = "Advanced",
                        price = "1500/-",
                        rating = "4.8",
                        videos = "25",
                        hours = "50 Hours"
                    ),
                    MentorCourseModel(
                        id = 6, // Maps to "3D Modeling for Beginners"
                        title = "3D Modeling for Beginners",
                        level = "Beginner",
                        price = "1300/-",
                        rating = "4.6",
                        videos = "20",
                        hours = "40 Hours"
                    ),
                    MentorCourseModel(
                        id = 7, // Maps to "Advanced 3D Sculpting"
                        title = "Advanced 3D Sculpting",
                        level = "Advanced",
                        price = "1700/-",
                        rating = "4.7",
                        videos = "28",
                        hours = "55 Hours"
                    ),
                    MentorCourseModel(
                        id = 8, // Maps to "3D Printing Essentials"
                        title = "3D Printing Essentials",
                        level = "Intermediate",
                        price = "1400/-",
                        rating = "4.5",
                        videos = "22",
                        hours = "45 Hours"
                    )
                ),
                reviews = listOf(
                    ReviewModel("Alex", "Great mentor!", "2 days ago"),
                    ReviewModel("John", "Very helpful and knowledgeable.", "1 week ago")
                )
            ),
            // Add other mentors similarly with correct course IDs
            MentorModel(
                id = 2,
                name = "Aman",
                profession = "Web Developer",
                courses = 2,
                students = 1200,
                ratings = 52,
                coursesList = listOf(
                    MentorCourseModel(
                        id = 9, // Maps to "Full-Stack Web Development with React & Node"
                        title = "Full-Stack Web Development with React & Node",
                        level = "Advanced",
                        price = "2000/-",
                        rating = "4.9",
                        videos = "30",
                        hours = "60 Hours"
                    ),
                    MentorCourseModel(
                        id = 10, // Maps to "Front-End Development with Vue.js"
                        title = "Front-End Development with Vue.js",
                        level = "Intermediate",
                        price = "1800/-",
                        rating = "4.8",
                        videos = "25",
                        hours = "50 Hours"
                    )
                ),
                reviews = listOf(
                    ReviewModel("Sara", "In-depth and well-explained.", "3 days ago"),
                    ReviewModel("Mike", "Helped me land a job!", "1 month ago")
                )
            )
//            MentorModel(
//                id = 3, name = "Rahul", profession = "Android Developer", courses = 2, students = 2000, ratings = 55,
//                coursesList = listOf(
//                    MentorCourseModel("Photoshop for Beginners", "Beginner", "1200/-", "4.5", "20", "40 Hours"),
//                    MentorCourseModel("Advanced 3D Sculpting", "Advanced", "1700/-", "4.7", "28", "55 Hours")
//                ),
//                reviews = listOf(
//                    ReviewModel("Emma", "Great introduction to Photoshop.", "1 week ago"),
//                    ReviewModel("Liam", "Very detailed and easy to follow.", "2 weeks ago")
//                )
//            ),
//            MentorModel(
//                id = 4, name = "Manev", profession = "UI/UX Designer", courses = 2, students = 950, ratings = 465,
//                coursesList = listOf(
//                    MentorCourseModel("Illustrator Mastery", "Advanced", "1800/-", "4.8", "30", "60 Hours"),
//                    MentorCourseModel("Graphic Design Basics", "Beginner", "1000/-", "4.4", "15", "30 Hours")
//                ),
//                reviews = listOf(
//                    ReviewModel("Sophia", "Amazing insights into UI/UX design.", "1 week ago"),
//                    ReviewModel("Oliver", "The best course on Illustrator!", "2 weeks ago")
//                )
//            ),
//            MentorModel(
//                id = 5, name = "Raghav", profession = "Game Developer", courses = 2, students = 3000, ratings = 57,
//                coursesList = listOf(
//                    MentorCourseModel("Master 3D Animation with Blender", "Advanced", "1500/-", "4.8", "25", "50 Hours"),
//                    MentorCourseModel("3D Printing Essentials", "Intermediate", "1400/-", "4.5", "22", "45 Hours")
//                ),
//                reviews = listOf(
//                    ReviewModel("James", "Fantastic course on Blender!", "3 days ago"),
//                    ReviewModel("Linda", "Great for learning 3D printing basics.", "1 month ago")
//                )
//            ),
//            MentorModel(
//                id = 6, name = "Mihir", profession = "Data Scientist", courses = 1, students = 1800, ratings = 42,
//                coursesList = listOf(
//                    MentorCourseModel("3D Modeling for Beginners", "Beginner", "1300/-", "4.6", "20", "40 Hours")
//                ),
//                reviews = listOf(
//                    ReviewModel("David", "Very informative and well-structured.", "2 weeks ago"),
//                    ReviewModel("Grace", "Good introduction to 3D modeling.", "1 month ago")
//                )
//            ),
//            MentorModel(
//                id = 7, name = "Mohit", profession = "AI Specialist", courses = 1, students = 2200, ratings = 54,
//                coursesList = listOf(
//                    MentorCourseModel("Advanced 3D Sculpting", "Advanced", "1700/-", "4.7", "28", "55 Hours")
//                ),
//                reviews = listOf(
//                    ReviewModel("Ethan", "Highly recommend this course.", "1 week ago"),
//                    ReviewModel("Charlotte", "Excellent content and delivery.", "2 weeks ago")
//                )
//            ),
//            MentorModel(
//                id = 8, name = "Sneha", profession = "Digital Marketing Specialist", courses = 1, students = 800, ratings = 44,
//                coursesList = listOf(
//                    MentorCourseModel("3D Printing Essentials", "Intermediate", "1400/-", "4.5", "22", "45 Hours")
//                ),
//                reviews = listOf(
//                    ReviewModel("Henry", "Great insights on digital marketing.", "3 days ago"),
//                    ReviewModel("Amelia", "Very practical and useful.", "1 month ago")
//                )
//            ),
//            MentorModel(
//                id = 9, name = "Pooja", profession = "Graphic Designer", courses = 1, students = 600, ratings = 325,
//                coursesList = listOf(
//                    MentorCourseModel("Full-Stack Web Development with React & Node", "Advanced", "2000/-", "4.9", "30", "60 Hours")
//                ),
//                reviews = listOf(
//                    ReviewModel("Lucas", "Excellent course on full-stack development.", "2 weeks ago"),
//                    ReviewModel("Mia", "Very thorough and detailed.", "1 month ago")
//                )
//            ),
//            MentorModel(
//                id = 10, name = "Rajesh", profession = "Cloud Architect", courses = 1, students = 2500, ratings = 525,
//                coursesList = listOf(
//                    MentorCourseModel("Front-End Development with Vue.js", "Intermediate", "1800/-", "4.8", "25", "50 Hours")
//                ),
//                reviews = listOf(
//                    ReviewModel("Nathan", "Great course on Vue.js development.", "3 days ago"),
//                    ReviewModel("Hannah", "Helped me understand front-end concepts.", "1 month ago")
//                )
//            ),
//            MentorModel(
//                id = 11, name = "Neha", profession = "Cybersecurity Expert", courses = 2, students = 1700, ratings = 452,
//                coursesList = listOf(
//                    MentorCourseModel("Backend Development with Django", "Intermediate", "1700/-", "4.7", "24", "48 Hours")
//                ),
//                reviews = listOf(
//                    ReviewModel("Jack", "Very informative course on Django.", "2 weeks ago"),
//                    ReviewModel("Ella", "Highly recommend for backend development.", "1 month ago")
//                )
//            ),
//            MentorModel(
//                id = 12, name = "Siddharth", profession = "Blockchain Developer", courses = 1, students = 1400, ratings = 552,
//                coursesList = listOf(
//                    MentorCourseModel("Responsive Web Design", "Beginner", "1500/-", "4.6", "20", "40 Hours")
//                ),
//                reviews = listOf(
//                    ReviewModel("Ryan", "Great course on responsive design.", "1 week ago"),
//                    ReviewModel("Ava", "Very helpful for beginners.", "2 weeks ago")
//                )
//            ),
//            MentorModel(
//                id = 13, name = "Kriti", profession = "Product Manager", courses = 1, students = 1000, ratings = 4252,
//                coursesList = listOf(
//                    MentorCourseModel("Digital Marketing Strategies", "Beginner", "1500/-", "4.5", "18", "36 Hours")
//                ),
//                reviews = listOf(
//                    ReviewModel("Leo", "Great introduction to digital marketing.", "3 days ago"),
//                    ReviewModel("Zoe", "Very practical and easy to follow.", "1 month ago")
//                )
//            ),
//            MentorModel(
//                id = 14, name = "Arjun", profession = "DevOps Engineer", courses = 2, students = 1600, ratings = 42,
//                coursesList = listOf(
//                    MentorCourseModel("Social Media Marketing", "Intermediate", "1400/-", "4.6", "20", "40 Hours"),
//                    MentorCourseModel("Content Marketing Mastery", "Advanced", "1600/-", "4.7", "22", "44 Hours")
//                ),
//                reviews = listOf(
//                    ReviewModel("Mason", "Excellent course on social media marketing.", "2 weeks ago"),
//                    ReviewModel("Lily", "Very detailed and comprehensive.", "1 month ago")
//                )
//            )
        )

        return mentors.map { mentor ->
            if (mentor.coursesList.size > 6) {
                mentor.copy(courses = mentor.coursesList.size)
            } else {
                mentor
            }
        }
    }

    // Get mentor by ID
    fun getMentorById(id: Int): MentorModel? {
        return getTopMentors().find { it.id == id }
    }
}
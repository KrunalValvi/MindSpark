package com.example.mindspark.courses.data

import com.example.mindspark.R
import com.example.mindspark.courses.model.MentorCourseModel
import com.example.mindspark.courses.model.MentorModel
import com.example.mindspark.courses.model.ReviewModel

object MentorData {

    fun getTopMentors(): List<MentorModel> {
        val mentors = listOf(
            MentorModel(
                id = 1,
                name = "Jiya",
                profession = "3D Design",
                courses = 4,
                students = 1500,
                ratings = 423,
                imageRes = R.drawable.mentor_jiya,
                coursesList = listOf(
                    MentorCourseModel(
                        id = 5,
                        title = "Master 3D Animation with Blender",
                        level = "Advanced",
                        price = "1500/-",
                        rating = "4.8",
                        videos = "25",
                        hours = "50 Hours"
                    ),
                    MentorCourseModel(
                        id = 6,
                        title = "3D Modeling for Beginners",
                        level = "Beginner",
                        price = "1300/-",
                        rating = "4.6",
                        videos = "20",
                        hours = "40 Hours"
                    ),
                    MentorCourseModel(
                        id = 7,
                        title = "Advanced 3D Sculpting",
                        level = "Advanced",
                        price = "1700/-",
                        rating = "4.7",
                        videos = "28",
                        hours = "55 Hours"
                    ),
                    MentorCourseModel(
                        id = 8,
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
            MentorModel(
                id = 2,
                name = "Mavji",
                profession = "Web Developer",
                courses = 2,
                students = 1200,
                ratings = 52,
                imageRes = R.drawable.mentor_mavji,
                coursesList = listOf(
                    MentorCourseModel(
                        id = 9,
                        title = "Full-Stack Web Development with React & Node",
                        level = "Advanced",
                        price = "2000/-",
                        rating = "4.9",
                        videos = "30",
                        hours = "60 Hours"
                    ),
                    MentorCourseModel(
                        id = 10,
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
            ),
            MentorModel(
                id = 3,
                name = "Rahul",
                profession = "Android Developer",
                courses = 2,
                students = 2000,
                ratings = 55,
                imageRes = R.drawable.mentor_rahul,
                coursesList = listOf(
                    MentorCourseModel(
                        id = 3,
                        title = "Photoshop for Beginners",
                        level = "Beginner",
                        price = "1200/-",
                        rating = "4.5",
                        videos = "20",
                        hours = "40 Hours"
                    ),
                    MentorCourseModel(
                        id = 7,
                        title = "Advanced 3D Sculpting",
                        level = "Advanced",
                        price = "1700/-",
                        rating = "4.7",
                        videos = "28",
                        hours = "55 Hours"
                    )
                ),
                reviews = listOf(
                    ReviewModel("Emma", "Great introduction to Photoshop.", "1 week ago"),
                    ReviewModel("Liam", "Very detailed and easy to follow.", "2 weeks ago")
                )
            ),
            MentorModel(
                id = 4,
                name = "Riya",
                profession = "UI/UX Designer",
                courses = 2,
                students = 950,
                ratings = 465,
                imageRes = R.drawable.mentor_riya,
                coursesList = listOf(
                    MentorCourseModel(
                        id = 4,
                        title = "Illustrator Mastery",
                        level = "Advanced",
                        price = "1800/-",
                        rating = "4.8",
                        videos = "30",
                        hours = "60 Hours"
                    ),
                    MentorCourseModel(
                        id = 1,
                        title = "Graphic Design Basics",
                        level = "Beginner",
                        price = "1000/-",
                        rating = "4.4",
                        videos = "15",
                        hours = "30 Hours"
                    )
                ),
                reviews = listOf(
                    ReviewModel("Sophia", "Amazing insights into UI/UX design.", "1 week ago"),
                    ReviewModel("Oliver", "The best course on Illustrator!", "2 weeks ago")
                )
            ),
            MentorModel(
                id = 5,
                name = "Raghav",
                profession = "Game Developer",
                courses = 2,
                students = 3000,
                ratings = 57,
                imageRes = R.drawable.mentor_raghav,
                coursesList = listOf(
                    MentorCourseModel(
                        id = 5,
                        title = "Master 3D Animation with Blender",
                        level = "Advanced",
                        price = "1500/-",
                        rating = "4.8",
                        videos = "25",
                        hours = "50 Hours"
                    ),
                    MentorCourseModel(
                        id = 8,
                        title = "3D Printing Essentials",
                        level = "Intermediate",
                        price = "1400/-",
                        rating = "4.5",
                        videos = "22",
                        hours = "45 Hours"
                    )
                ),
                reviews = listOf(
                    ReviewModel("James", "Fantastic course on Blender!", "3 days ago"),
                    ReviewModel("Linda", "Great for learning 3D printing basics.", "1 month ago")
                )
            ),
            MentorModel(
                id = 6,
                name = "Mihir",
                profession = "Data Scientist",
                courses = 1,
                students = 1800,
                ratings = 42,
                imageRes = R.drawable.mentor_mihir,
                coursesList = listOf(
                    MentorCourseModel(
                        id = 6,
                        title = "3D Modeling for Beginners",
                        level = "Beginner",
                        price = "1300/-",
                        rating = "4.6",
                        videos = "20",
                        hours = "40 Hours"
                    )
                ),
                reviews = listOf(
                    ReviewModel("David", "Very informative and well-structured.", "2 weeks ago"),
                    ReviewModel("Grace", "Good introduction to 3D modeling.", "1 month ago")
                )
            ),
            MentorModel(
                id = 7,
                name = "Mohit",
                profession = "AI Specialist",
                courses = 1,
                students = 2200,
                ratings = 54,
                imageRes = R.drawable.mentor_mohit,
                coursesList = listOf(
                    MentorCourseModel(
                        id = 7,
                        title = "Advanced 3D Sculpting",
                        level = "Advanced",
                        price = "1700/-",
                        rating = "4.7",
                        videos = "28",
                        hours = "55 Hours"
                    )
                ),
                reviews = listOf(
                    ReviewModel("Ethan", "Highly recommend this course.", "1 week ago"),
                    ReviewModel("Charlotte", "Excellent content and delivery.", "2 weeks ago")
                )
            ),
            MentorModel(
                id = 8,
                name = "Sneha",
                profession = "Digital Marketing Specialist",
                courses = 1,
                students = 800,
                ratings = 44,
                imageRes = R.drawable.mentor_sneha,
                coursesList = listOf(
                    MentorCourseModel(
                        id = 8,
                        title = "3D Printing Essentials",
                        level = "Intermediate",
                        price = "1400/-",
                        rating = "4.5",
                        videos = "22",
                        hours = "45 Hours"
                    )
                ),
                reviews = listOf(
                    ReviewModel("Henry", "Great insights on digital marketing.", "3 days ago"),
                    ReviewModel("Amelia", "Very practical and useful.", "1 month ago")
                )
            ),
            MentorModel(
                id = 9,
                name = "Pooja",
                profession = "Graphic Designer",
                courses = 1,
                students = 600,
                ratings = 325,
                imageRes = R.drawable.mentor_pooja,
                coursesList = listOf(
                    MentorCourseModel(
                        id = 9,
                        title = "Full-Stack Web Development with React & Node",
                        level = "Advanced",
                        price = "2000/-",
                        rating = "4.9",
                        videos = "30",
                        hours = "60 Hours"
                    )
                ),
                reviews = listOf(
                    ReviewModel("Lucas", "Excellent course on full-stack development.", "2 weeks ago"),
                    ReviewModel("Mia", "Very thorough and detailed.", "1 month ago")
                )
            ),
            MentorModel(
                id = 10,
                name = "Rajesh",
                profession = "Cloud Architect",
                courses = 1,
                students = 2500,
                ratings = 525,
                imageRes = R.drawable.mentor_rajesh,
                coursesList = listOf(
                    MentorCourseModel(
                        id = 10,
                        title = "Front-End Development with Vue.js",
                        level = "Intermediate",
                        price = "1800/-",
                        rating = "4.8",
                        videos = "25",
                        hours = "50 Hours"
                    )
                ),
                reviews = listOf(
                    ReviewModel("Nathan", "Great course on Vue.js development.", "3 days ago"),
                    ReviewModel("Hannah", "Helped me understand front-end concepts.", "1 month ago")
                )
            ),
            MentorModel(
                id = 11,
                name = "Neha",
                profession = "Cybersecurity Expert",
                courses = 2,
                students = 1700,
                ratings = 452,
                imageRes = R.drawable.mentor_neha,
                coursesList = listOf(
                    MentorCourseModel(
                        id = 11,
                        title = "Backend Development with Django",
                        level = "Intermediate",
                        price = "1700/-",
                        rating = "4.7",
                        videos = "24",
                        hours = "48 Hours"
                    )
                ),
                reviews = listOf(
                    ReviewModel("Jack", "Very informative course on Django.", "2 weeks ago"),
                    ReviewModel("Ella", "Highly recommend for backend development.", "1 month ago")
                )
            ),
            MentorModel(
                id = 12,
                name = "Siddharth",
                profession = "Blockchain Developer",
                courses = 1,
                students = 1400,
                ratings = 552,
                imageRes = R.drawable.mentor_siddharth,
                coursesList = listOf(
                    MentorCourseModel(
                        id = 12,
                        title = "Responsive Web Design",
                        level = "Beginner",
                        price = "1500/-",
                        rating = "4.6",
                        videos = "20",
                        hours = "40 Hours"
                    )
                ),
                reviews = listOf(
                    ReviewModel("Ryan", "Great course on responsive design.", "1 week ago"),
                    ReviewModel("Ava", "Very helpful for beginners.", "2 weeks ago")
                )
            ),
            MentorModel(
                id = 13,
                name = "Kriti",
                profession = "Product Manager",
                courses = 1,
                students = 1000,
                ratings = 4252,
                imageRes = R.drawable.mentor_kriti,
                coursesList = listOf(
                    MentorCourseModel(
                        id = 13,
                        title = "Digital Marketing Strategies",
                        level = "Beginner",
                        price = "1500/-",
                        rating = "4.5",
                        videos = "18",
                        hours = "36 Hours"
                    )
                ),
                reviews = listOf(
                    ReviewModel("Leo", "Great introduction to digital marketing.", "3 days ago"),
                    ReviewModel("Zoe", "Very practical and easy to follow.", "1 month ago")
                )
            ),
            MentorModel(
                id = 14,
                name = "Arjun",
                profession = "DevOps Engineer",
                courses = 2,
                students = 1600,
                ratings = 42,
                imageRes = R.drawable.mentor_arjun,
                coursesList = listOf(
                    MentorCourseModel(
                        id = 14,
                        title = "Social Media Marketing",
                        level = "Intermediate",
                        price = "1400/-",
                        rating = "4.6",
                        videos = "20",
                        hours = "40 Hours"
                    ),
                    MentorCourseModel(
                        id = 15,
                        title = "Content Marketing Mastery",
                        level = "Advanced",
                        price = "1600/-",
                        rating = "4.7",
                        videos = "22",
                        hours = "44 Hours"
                    )
                ),
                reviews = listOf(
                    ReviewModel("Mason", "Excellent course on social media marketing.", "2 weeks ago"),
                    ReviewModel("Lily", "Very detailed and comprehensive.", "1 month ago")
                )
            )
        )

        return mentors.map { mentor ->
            if (mentor.coursesList.size > 6) {
                mentor.copy(courses = mentor.coursesList.size)
            } else {
                mentor
            }
        }
    }

    fun getMentorById(id: Int): MentorModel? {
        return getTopMentors().find { it.id == id }
    }
}
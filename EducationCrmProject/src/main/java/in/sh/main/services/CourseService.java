package in.sh.main.services;

import in.sh.main.entities.Course;
import in.sh.main.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
   private CourseRepository courseRepository;
    public List<Course> getAllCourseDetails(){
        return courseRepository.findAll();
    }
}

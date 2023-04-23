package utc.edu.thesis.domain.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "created_date")
    private LocalDateTime createDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @Column(name = "outline_file")
    private String outlineFile;
    @Column(name = "report_file")
    private String reportFile;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
}

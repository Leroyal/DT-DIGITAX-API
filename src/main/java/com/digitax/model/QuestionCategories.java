package com.digitax.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;

@Entity
@Table(name = "question_categories")
@Getter
@Setter
public class QuestionCategories {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
   
    
	@Column(name = "title")
    private String title;
	
	
	@Column(name = "categoryLabel")
    private String categoryLabel;
	 
    
	@Column(name = "image")
    private String image;
    
	@Column(name = "numberOfQuestions")
    private String numberOfQuestions;

    
    @CreatedDate
    @Column(name = "created_at")
    private Long createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Long updatedAt;
    
    public QuestionCategories() {
    	
    }

	@Override
	public String toString() {
		return "QuestionCategories [id=" + id + ", title=" + title + ", image=" + image + ", numberOfQuestions="
				+ numberOfQuestions + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
    
    


}

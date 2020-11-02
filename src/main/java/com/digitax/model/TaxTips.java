package com.digitax.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;

@Entity
@Table(name = "tax_tips")
@Getter
@Setter
public class TaxTips {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
   
    
	@Column(name = "title")
    private String title;
	
	
	@Column(name = "taxTipLabel")
    private String taxTipLabel;
	 
    
	@Column(name = "image")
    private String image;
    
	@Column(name = "numberOfQuestions")
    private String numberOfQuestions;
	
	
	@Column(name = "is_visible")
    private Boolean isVisible;

    
    @CreatedDate
    @Column(name = "created_at")
    private Long createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Long updatedAt;
    
    public TaxTips() {
    	
    }

	@Override
	public String toString() {
		return "QuestionCategories [id=" + id + ", title=" + title + ", image=" + image + ", numberOfQuestions="
				+ numberOfQuestions + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
    
    


}

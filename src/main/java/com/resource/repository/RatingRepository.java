package com.resource.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resource.entity.Rating;
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer>{

	/**
	 * @author Sri Keerthna.
	 * @since 2020-01-30.
	 * This method will filter the ratings that are below average and poor.
	 * @param ratingScale1 - Poor Rating scale
	 * @param ratingScale2 - Below Average Rating scale
	 * @return List of ratings are filtered from database.
	 */
	List<Rating> findByRatingScaleOrRatingScaleContainingIgnoreCase(String ratingScale1, String ratingScale2);

}

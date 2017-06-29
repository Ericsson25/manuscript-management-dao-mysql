package manuscript.module.manuscript.management.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import manuscript.module.manuscript.management.bean.Author;
import manuscript.module.manuscript.management.bean.CheckSubmissionExistence;
import manuscript.module.manuscript.management.bean.Submission;
import manuscript.module.manuscript.management.request.SaveSubmissionRequest;
import manuscript.module.user.management.bean.AcademicDisciplines;

public interface ManuscriptDaoMapper {

	public void insertSubmission(@Param("saveRequest") SaveSubmissionRequest saveRequest);

	public void insertSubmissionDoc(@Param("saveRequest") SaveSubmissionRequest saveRequest);

	public void insertSubmissionAuthor(@Param("submissionId") String submissionId, @Param("userId") String userId);

	public Submission getSubmissionBySubmissionId(@Param("submissionId") String submissionId);

	public List<Author> getSubmissionAuthorList(@Param("submissionId") String submissionId);

	public List<AcademicDisciplines> getSubmissionDisciplinesList(@Param("submissionId") String submissionId);

	public String checkSubmissionExistence(@Param("checkSubmissionExistence") CheckSubmissionExistence checkSubmissionExistence);

	public void updateSubmissionData(@Param("submission") Submission submission);

	public void removeSubmissionAuthorsBySubmissionId(@Param("submissionId") String submissionId);

	public void removeSubmissionDisciplinesBySubmissionId(@Param("submissionId") String submissionId);

	public void insertSubmissionDisciplines(@Param("academicDisciplines") AcademicDisciplines academicDisciplines,
			@Param("submissionId") String submissionId);

}

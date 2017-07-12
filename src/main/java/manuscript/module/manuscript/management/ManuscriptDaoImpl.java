package manuscript.module.manuscript.management;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import manuscript.module.manuscript.management.bean.Author;
import manuscript.module.manuscript.management.bean.CheckSubmissionExistence;
import manuscript.module.manuscript.management.bean.Submission;
import manuscript.module.manuscript.management.exception.CheckSubmissionExistenceException;
import manuscript.module.manuscript.management.mapper.ManuscriptDaoMapper;
import manuscript.module.manuscript.management.request.RemoveSubmissionRequest;
import manuscript.module.manuscript.management.request.SaveSubmissionDataRequest;
import manuscript.module.manuscript.management.request.SaveSubmissionRequest;
import manuscript.module.manuscript.management.response.GetSubmissionDataResponse;
import manuscript.module.user.management.bean.AcademicDisciplines;

@Repository
public class ManuscriptDaoImpl implements ManuscriptDao {

	private ManuscriptDaoMapper manuscriptDaoMapper;

	public ManuscriptDaoImpl(ManuscriptDaoMapper manuscriptDaoMapper) {
		this.manuscriptDaoMapper = manuscriptDaoMapper;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void saveBasicSubmissionData(SaveSubmissionRequest saveSubmissionRequest) {
		manuscriptDaoMapper.insertSubmission(saveSubmissionRequest);
		manuscriptDaoMapper.insertSubmissionDoc(saveSubmissionRequest);
		manuscriptDaoMapper.insertSubmissionAuthor(saveSubmissionRequest.getSubmissionId(), saveSubmissionRequest.getAuthorId());
	}

	@Override
	public GetSubmissionDataResponse getSubmissionData(String submissionId) {
		GetSubmissionDataResponse submissionDataResponse = new GetSubmissionDataResponse();
		Submission submission = manuscriptDaoMapper.getSubmissionBySubmissionId(submissionId);
		submission.setAcademicDisciplines(manuscriptDaoMapper.getSubmissionDisciplinesList(submissionId));
		submission.setAuthors(manuscriptDaoMapper.getSubmissionAuthorList(submissionId));
		submissionDataResponse.setSubmission(submission);
		return submissionDataResponse;
	}

	@Override
	public String checkSubmissionExistence(CheckSubmissionExistence checkSubmissionExistence) {
		String submissionPath = manuscriptDaoMapper.checkSubmissionExistence(checkSubmissionExistence);
		if (submissionPath == null || submissionPath.isEmpty()) {
			throw new CheckSubmissionExistenceException("Submission is not present in the database.");
		}

		return submissionPath;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void saveSubmissionData(SaveSubmissionDataRequest submission) {
		manuscriptDaoMapper.updateSubmissionData(submission.getSubmission());

		manuscriptDaoMapper.removeSubmissionAuthorsBySubmissionId(submission.getSubmission().getSubmissionId());
		for (Author author : submission.getSubmission().getAuthors()) {
			manuscriptDaoMapper.insertSubmissionAuthor(submission.getSubmission().getSubmissionId(), author.getUserId());
		}

		manuscriptDaoMapper.removeSubmissionDisciplinesBySubmissionId(submission.getSubmission().getSubmissionId());

		for (AcademicDisciplines academicDisciplines : submission.getSubmission().getAcademicDisciplines()) {
			manuscriptDaoMapper.insertSubmissionDisciplines(academicDisciplines, submission.getSubmission().getSubmissionId());
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public String removeSubmissionData(RemoveSubmissionRequest request) {
		manuscriptDaoMapper.removeSubmissionDisciplinesBySubmissionId(request.getSubmissionId());
		manuscriptDaoMapper.removeSubmissionAuthorsBySubmissionId(request.getSubmissionId());

		String filePath = manuscriptDaoMapper.getSubmissionDocPath(request.getSubmissionId());
		manuscriptDaoMapper.removeSubmissionDocBySubmissionId(request.getSubmissionId());
		manuscriptDaoMapper.removeSubmissionBySubmissionId(request.getSubmissionId());
		return filePath;
	}

}

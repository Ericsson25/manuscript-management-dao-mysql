package manuscript.module.manuscript.management;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import manuscript.module.manuscript.management.bean.Submission;
import manuscript.module.manuscript.management.mapper.ManuscriptDaoMapper;
import manuscript.module.manuscript.management.request.SaveSubmissionRequest;
import manuscript.module.manuscript.management.response.GetSubmissionDataResponse;

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

}

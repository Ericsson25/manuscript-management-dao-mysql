package manuscript.module.manuscript.management.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import manuscript.module.manuscript.management.ManuscriptDao;
import manuscript.module.manuscript.management.response.GetSubmissionDataResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = ManuscriptDaoContext.class)
@Transactional
public class ManuscriptDaoTest {

	@Autowired
	private ManuscriptDao manuscriptDao;

	@Test
	public void getSubmissionBySubmissionId_with_succcess_test() {
		GetSubmissionDataResponse submissionDataResponse = new GetSubmissionDataResponse();

		submissionDataResponse = manuscriptDao.getSubmissionData("Jonsnow@20170624_2221_1");

		Assert.assertNotNull("submission must be not null", submissionDataResponse);
	}

	// @Test
	// public void getSubmissionDisciplinesList_with_succcess_test() {
	// List<AcademicDisciplines> disciplines = (manuscriptDao.getSubmissionData("Jonsnow@20170624_2221_1")).getSubmission().getAcademicDisciplines();
	// Assert.assertNotNull("submission must be not null", disciplines);
	// }
}

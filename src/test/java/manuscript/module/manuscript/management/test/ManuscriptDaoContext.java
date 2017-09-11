package manuscript.module.manuscript.management.test;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import manuscript.module.manuscript.management.ManuscriptDao;
import manuscript.module.manuscript.management.ManuscriptDaoImpl;
import manuscript.module.manuscript.management.mapper.ManuscriptDaoMapper;
import manuscript.test.dao.context.AbstractDaoTestContext;

@Configuration
@MapperScan("manuscript.module.manuscript.management.mapper")
public class ManuscriptDaoContext extends AbstractDaoTestContext {

	@Autowired
	private ManuscriptDaoMapper manuscriptDaoMapper;

	@Bean
	public ManuscriptDao getManuscriptDao() {
		return new ManuscriptDaoImpl(manuscriptDaoMapper);
	}

	@Override
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(DATABASE_DRIVER_CLASS);
		dataSource.setUrl(DATABASE_URL);
		dataSource.setUsername(DATABASE_USER_NAME);
		dataSource.setPassword(DATABASE_PASSWORD);
		return dataSource;
	}

	public static final String DATABASE_DRIVER_CLASS = "com.mysql.jdbc.Driver";
	public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/manuscript";
	public static final String DATABASE_USER_NAME = "manuscript";
	public static final String DATABASE_PASSWORD = "p*P4druxab";

}

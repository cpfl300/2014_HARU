package haru.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages={"haru.dao"})
@PropertySource(value="classpath:application-properties.xml")
public class TestConfig {
	@Resource
	private Environment env;
	
	@Bean
	public DataSource dataSource(){
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(env.getRequiredProperty("database.driverClassName"));
		ds.setUrl(env.getRequiredProperty("database.testUrl"));
		ds.setUsername(env.getRequiredProperty("database.username"));
		ds.setPassword(env.getRequiredProperty("database.password"));
		return ds;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}
}
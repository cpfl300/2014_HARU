package refinery.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import refinery.model.Journal;

@Repository
public class JournalDao {
	
	private static final Logger log = LoggerFactory.getLogger(JournalDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Journal> journalMapper = (rs, rowNum) -> {
		
		Journal journal = new Journal();
		journal.setId(rs.getInt("journals.id"));
		journal.setName(rs.getString("journals.name"));
		journal.setSection(rs.getString("section.name"));
		
		return journal;
		
	};

	public Journal getByName(String name) {
		log.debug("journal: " + name);
		
		return this.jdbcTemplate.queryForObject(
					"SELECT journals.id, journals.name, section.name FROM journals INNER JOIN journal_sections AS section ON journals.journal_sections_id = section.id WHERE journals.name = ? ",
					new Object[]{name},
					this.journalMapper 				
				);
		
	}

	public Journal get(int id) {
		
		return this.jdbcTemplate.queryForObject(
				"SELECT journals.id, journals.name, section.name FROM journals INNER JOIN journal_sections AS section ON journals.journal_sections_id = section.id WHERE journals.id = ? ",
				new Object[]{id},
				this.journalMapper 				
			);
	}

}

package refinery.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import refinery.model.Section;


@Repository
public class SectionDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Section> sectionMapper = (rs, rowNum) -> {
		Section section = new Section();
		section.setId(rs.getInt("minor.id"));
		section.setMajor(rs.getString("major.name"));
		section.setMinor(rs.getString("minor.name"));
		
		return section;
		
	};

	public Section getByMinor(String minor) {
		
		return this.jdbcTemplate.queryForObject(
					"SELECT minor.id, minor.name, major.name FROM minor_sections AS minor INNER JOIN major_sections AS major ON minor.major_sections_id = major.id WHERE minor.name = ?",
					new Object[]{minor},
					this.sectionMapper 
				);

	}

	public Section get(int id) {
		
		return this.jdbcTemplate.queryForObject(
				"SELECT minor.id, minor.name, major.name FROM minor_sections AS minor INNER JOIN major_sections AS major ON minor.major_sections_id = major.id WHERE minor.id = ?",
				new Object[]{id},
				this.sectionMapper 
			);
	}
	
}

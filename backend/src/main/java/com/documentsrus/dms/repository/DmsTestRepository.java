package com.documentsrus.dms.repository;

import com.documentsrus.dms.model.Document;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Qualifier("dmsTestRepository")
public class DmsTestRepository implements DmsRepositoryInterface {

    JdbcTemplate jdbcTemplate;

    DmsTestRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Document getDocument(int id) throws Exception {
        BasicDataSource dataSource = new BasicDataSource();

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("sp_readDocument")
                .returningResultSet("documents", new RowMapper<Document>() {

                    @Override
                    public Document mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Document document = new Document();

                        document.setId(rs.getInt("id_Document"));
                        document.setName(rs.getString("name_Document"));
                        document.setType(rs.getString("type_Document"));
                        document.setDescription(rs.getString("description_Document"));
                        document.setDocument(rs.getBytes("file_Document"));

                        return document;
                    }
                });

        Map<String, Object> inParamMap = new HashMap<String, Object>();
        inParamMap.put("id", id);
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);

        Map<String, Object> out = simpleJdbcCall.execute(in);

        List<Document> listDocuments = (List<Document>) out.get("documents");

        return listDocuments.get(0);
    }
}

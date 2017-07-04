package com.semanticRelationsExtractor.database;

import com.semanticRelationsExtractor.data.SemanticExtractionData;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.logging.Logger;

/**
 * Created by Oliver on 5/17/2017.
 */
public class DatabaseAccessorImpl implements DatabaseAccessor {

    private JdbcTemplate jdbcTemplate;

    public DatabaseAccessorImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final static Logger LOGGER = Logger.getLogger(DatabaseAccessorImpl.class.getName());

    @Override
    public boolean existsSemanticDataInDatabase(SemanticExtractionData semanticExtractionData) {
        final String sql = "select id from jos_nlp_semantic_data where atomic_subject = ? and extended_subject = ? and " +
                "atomic_verb_predicate = ? and extended_verb_predicate = ? and atomic_noun_predicate = ? and extended_noun_predicate = ?";
        try {
            jdbcTemplate.queryForInt(sql, new Object[]{semanticExtractionData.getAtomicSubject(), semanticExtractionData.getExtendedSubject(),
                    semanticExtractionData.getAtomicVerbPredicate(), semanticExtractionData.getExtendedVerbPredicate(), semanticExtractionData.getAtomicNounPredicate(),
                    semanticExtractionData.getExtendedNounPredicate()});
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public void insertSemanticData(SemanticExtractionData semanticExtractionData) {
        final String sql = "insert into jos_nlp_semantic_data (atomic_subject,extended_subject,atomic_verb_predicate,extended_verb_predicate," +
                "atomic_noun_predicate,extended_noun_predicate, sentence, object) values (?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{semanticExtractionData.getAtomicSubject(), semanticExtractionData.getExtendedSubject(),
                semanticExtractionData.getAtomicVerbPredicate(),
                semanticExtractionData.getExtendedVerbPredicate(), semanticExtractionData.getAtomicNounPredicate(),
                semanticExtractionData.getExtendedNounPredicate(), semanticExtractionData.getSentence(), semanticExtractionData.getObject()});
        LOGGER.info("Inserting semantic extraction data: " + semanticExtractionData.getAtomicSubject() + " | " + semanticExtractionData.getExtendedSubject()
                + "|" + semanticExtractionData.getAtomicVerbPredicate() + "|" + semanticExtractionData.getExtendedVerbPredicate() + "|" +
                semanticExtractionData.getAtomicNounPredicate() + "|" + semanticExtractionData.getExtendedNounPredicate() + "|" + semanticExtractionData.getSentence()
                + "|" + semanticExtractionData.getObject());
    }
}

package com.cxp.springbootmybatis.principle.config;

import com.cxp.springbootmybatis.principle.utils.DOMUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.scripting.xmltags.XMLScriptBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 程
 * @date 2019/7/8 上午9:45
 */
@Component
public class PrincipleConfig {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private Configuration configuration;

    private MappedStatement ms;

    private void parse(String content, Map<String, Object> param){
        this.configuration = sqlSessionTemplate.getConfiguration();
        Document doc = DOMUtils.parseXMLDocument(content);
        XPathParser xPathParser = new XPathParser(doc, false);
        Node node = doc.getFirstChild();
        XNode xNode = new XNode(xPathParser, node, null);
        XMLScriptBuilder xmlScriptBuilder = new XMLScriptBuilder(configuration, xNode);
        SqlSource sqlSource = xmlScriptBuilder.parseScriptNode();

        MappedStatement.Builder builder = new MappedStatement.Builder(configuration, content.toString(), sqlSource, null);
        List<ResultMap> resultMaps = new ArrayList<>();
        List<ResultMapping> resultMappings = new ArrayList<>();

        ResultMap.Builder resultMapBuilder = new ResultMap.Builder(configuration, content.toString(), Map.class, resultMappings, true);
        resultMaps.add(resultMapBuilder.build());
        ms = builder.resultMaps(resultMaps).build();
    }

    public <E> List<E> executorList(Map<String, Object> param,String content,DataSource dataSource){
        parse(content,param);
        JdbcTransactionFactory transactionFactory1 = new JdbcTransactionFactory();
        Transaction transaction = transactionFactory1.newTransaction(dataSource, TransactionIsolationLevel.REPEATABLE_READ, false);
        SimpleExecutor executor = new SimpleExecutor(configuration,transaction);
        try {
            List<E> query = executor.query(ms, param, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}

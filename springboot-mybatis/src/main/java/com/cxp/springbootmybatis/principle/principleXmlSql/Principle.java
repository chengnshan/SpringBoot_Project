package com.cxp.springbootmybatis.principle.principleXmlSql;

import com.cxp.springbootmybatis.principle.utils.DOMUtils;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.scripting.xmltags.XMLScriptBuilder;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.sql.SQLException;
import java.util.*;

/**
 * @author 程
 * @date 2019/7/6 下午12:53
 */
public class Principle {
    public static void main(String[] args) {
       String content = "" +
               "<select id=\"getStudentById\" parameterType=\"Integer\" resultType=\"map\">\n" +
               "        select id,name,age,birthday,address from student_info where id = #{id}\n" +
               "    </select>";

       content = "<select id=\"findStudentByObj\" parameterType=\"map\" resultType=\"map\">\n" +
               " select id,name,age,birthday,address from student_info " +
               "<where>\n" +
               "            <if test=\"id != null\">\n" +
               "                id = #{id}\n" +
               "            </if>\n" +
               "            <if test=\" name != null and name != '' \">\n" +
               "                and name = #{name}\n" +
               "            </if>\n" +
               "            <if test=\" age != null \">\n" +
               "                and age = #{age}\n" +
               "            </if>\n" +
               "            <if test=\" address != null and address != '' \">\n" +
               "                and address = #{address}\n" +
               "            </if>\n" +
               "            <if test=\" birthday != null  \">\n" +
               "                and birthday = #{birthday, jdbcType=DATE}\n" +
               "            </if>\n" +
               "        </where>" +
               "    </select>";

        Principle p = new Principle();
        Map<String, Object> param = new HashMap<>();
        param.put("name","张三丰");
        param.put("age",122);
        param.put("address","湖北武当山");
        String sql = p.kylinSql(content, param);


        p.executorList(p.configuration,param,content);

    }

    private SqlSessionTemplate sqlSessionTemplate;

    private Configuration configuration;

    private MappedStatement ms;

    public String kylinSql(String content, Map<String, Object> param) {
        BoundSql boundSql = getBoundSql(content, param);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String resultSql = boundSql.getSql();
        Map<String,Object> objectMap =  (Map)boundSql.getParameterObject();

        for(ParameterMapping mapping : parameterMappings){
            if(boundSql.getAdditionalParameter(mapping.getProperty())==null){
                resultSql = resultSql.replaceFirst("[?]",objectMap.get(mapping.getProperty()).toString());
                continue;
            }
            resultSql = resultSql.replaceFirst("[?]", boundSql.getAdditionalParameter(mapping.getProperty()).toString());
        }
        System.out.println("最终sql为: "+ resultSql);
        return resultSql;
    }

    private SqlSessionTemplate getSqlsessionTemplate(){
        XMLConfigBuilder builder = new XMLConfigBuilder(Principle.class.getResourceAsStream(
                "mybatis-config.xml"));
        Configuration configuration = builder.parse();

        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sessionFactory = factoryBuilder.build(configuration);
        /*SqlSession sqlSession = sessionFactory.openSession();
        Object one = sqlSession.selectOne(
                "StudentInfoMapper.getStudentById", 1);*/
        return new SqlSessionTemplate(sessionFactory);
    }

    public BoundSql getBoundSql(String content, Map<String, Object> param){
        sqlSessionTemplate = getSqlsessionTemplate();
        this.configuration = sqlSessionTemplate.getConfiguration();
        Document doc = DOMUtils.parseXMLDocument(content);
        XPathParser xPathParser = new XPathParser(doc, false);
        Node node = doc.getFirstChild();
        XNode xNode = new XNode(xPathParser, node, null);
        XMLScriptBuilder xmlScriptBuilder = new XMLScriptBuilder(configuration, xNode);
        SqlSource sqlSource = xmlScriptBuilder.parseScriptNode();


        /*MapperBuilderAssistant builderAssistant = new MapperBuilderAssistant(configuration, null);
        XMLStatementBuilder xmlStatementBuilder = new XMLStatementBuilder(configuration,
                builderAssistant,xNode,null);
        xmlStatementBuilder.parseStatementNode();*/

        MappedStatement.Builder builder = new MappedStatement.Builder(configuration, content.toString(), sqlSource, null);
        List<ResultMap> resultMaps = new ArrayList<>();
        List<ResultMapping> resultMappings = new ArrayList<>();


        ResultMap.Builder resultMapBuilder = new ResultMap.Builder(configuration, content.toString(), Map.class, resultMappings, true);
        resultMaps.add(resultMapBuilder.build());
        ms = builder.resultMaps(resultMaps).build();
        BoundSql boundSql = ms.getBoundSql(param);

        return boundSql;
    }

    public <T> List<T> executorList(Configuration configuration,Map<String, Object> param,String content){
        Environment environment = configuration.getEnvironment();
        TransactionFactory transactionFactory = environment.getTransactionFactory();
        Transaction transaction = transactionFactory.newTransaction(environment.getDataSource(),TransactionIsolationLevel.REPEATABLE_READ,false);
        SimpleExecutor executor = new SimpleExecutor(configuration,transaction);
        try {
            List<T> query = executor.query(ms, param, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
            System.out.println(query);
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

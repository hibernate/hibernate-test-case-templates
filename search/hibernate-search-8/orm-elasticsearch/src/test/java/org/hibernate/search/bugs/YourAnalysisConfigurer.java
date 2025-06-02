package org.hibernate.search.bugs;

import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurationContext;
import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurer;

public class YourAnalysisConfigurer implements ElasticsearchAnalysisConfigurer {
	@Override
	public void configure(ElasticsearchAnalysisConfigurationContext context) {
		context.analyzer( "nameAnalyzer" ).custom()
				.tokenizer( "whitespace" )
				.tokenFilters( "lowercase", "asciifolding" );
	}
}

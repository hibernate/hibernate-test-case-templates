package org.hibernate.search.bugs;

import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;

public class YourAnalysisConfigurer implements LuceneAnalysisConfigurer {
	@Override
	public void configure(LuceneAnalysisConfigurationContext context) {
		context.analyzer( "nameAnalyzer" ).custom()
				.tokenizer( WhitespaceTokenizerFactory.class )
				.tokenFilter( LowerCaseFilterFactory.class )
				.tokenFilter( ASCIIFoldingFilterFactory.class );
	}
}

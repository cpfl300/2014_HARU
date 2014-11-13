package haru.filter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.content.tagrules.html.DivExtractingTagRuleBundle;

public class SiteMeshFilter extends ConfigurableSiteMeshFilter {

	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		builder
		.addDecoratorPaths("*", "/decorators/header", "/decorators/layout");
		
		builder
		.addTagRuleBundle (new DivExtractingTagRuleBundle());
	}

}

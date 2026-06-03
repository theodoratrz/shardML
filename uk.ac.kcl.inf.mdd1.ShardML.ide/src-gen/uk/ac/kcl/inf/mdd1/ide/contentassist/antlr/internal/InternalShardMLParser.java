package uk.ac.kcl.inf.mdd1.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import uk.ac.kcl.inf.mdd1.services.ShardMLGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalShardMLParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_ID", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'postgres'", "'mysql'", "'hash'", "'consistent_hash'", "'replicated'", "'allow'", "'warn'", "'deny'", "'read'", "'write'", "'delete'", "'='", "'>'", "'<'", "'<='", "'>='", "'!='", "'AND'", "'OR'", "'ASC'", "'DESC'", "'import'", "'database'", "'{'", "'type:'", "'}'", "'has_many'", "'has_one'", "'belongs_to'", "'shard'", "'strategy:'", "'key:'", "'buckets:'", "'colocate_with:'", "'route'", "'policy:'", "'max_conditions:'", "'query'", "'where:'", "'sort:'", "'group_by:'", "'distinct:'", "'?'", "'true'"
    };
    public static final int T__50=50;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__11=11;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int RULE_ID=5;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=6;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int RULE_STRING=4;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_WS=9;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;

    // delegates
    // delegators


        public InternalShardMLParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalShardMLParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalShardMLParser.tokenNames; }
    public String getGrammarFileName() { return "InternalShardML.g"; }


    	private ShardMLGrammarAccess grammarAccess;

    	public void setGrammarAccess(ShardMLGrammarAccess grammarAccess) {
    		this.grammarAccess = grammarAccess;
    	}

    	@Override
    	protected Grammar getGrammar() {
    		return grammarAccess.getGrammar();
    	}

    	@Override
    	protected String getValueForTokenName(String tokenName) {
    		return tokenName;
    	}



    // $ANTLR start "entryRuleModel"
    // InternalShardML.g:53:1: entryRuleModel : ruleModel EOF ;
    public final void entryRuleModel() throws RecognitionException {
        try {
            // InternalShardML.g:54:1: ( ruleModel EOF )
            // InternalShardML.g:55:1: ruleModel EOF
            {
             before(grammarAccess.getModelRule()); 
            pushFollow(FOLLOW_1);
            ruleModel();

            state._fsp--;

             after(grammarAccess.getModelRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // InternalShardML.g:62:1: ruleModel : ( ( rule__Model__Group__0 ) ) ;
    public final void ruleModel() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:66:2: ( ( ( rule__Model__Group__0 ) ) )
            // InternalShardML.g:67:2: ( ( rule__Model__Group__0 ) )
            {
            // InternalShardML.g:67:2: ( ( rule__Model__Group__0 ) )
            // InternalShardML.g:68:3: ( rule__Model__Group__0 )
            {
             before(grammarAccess.getModelAccess().getGroup()); 
            // InternalShardML.g:69:3: ( rule__Model__Group__0 )
            // InternalShardML.g:69:4: rule__Model__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Model__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getModelAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleImport"
    // InternalShardML.g:78:1: entryRuleImport : ruleImport EOF ;
    public final void entryRuleImport() throws RecognitionException {
        try {
            // InternalShardML.g:79:1: ( ruleImport EOF )
            // InternalShardML.g:80:1: ruleImport EOF
            {
             before(grammarAccess.getImportRule()); 
            pushFollow(FOLLOW_1);
            ruleImport();

            state._fsp--;

             after(grammarAccess.getImportRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleImport"


    // $ANTLR start "ruleImport"
    // InternalShardML.g:87:1: ruleImport : ( ( rule__Import__Group__0 ) ) ;
    public final void ruleImport() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:91:2: ( ( ( rule__Import__Group__0 ) ) )
            // InternalShardML.g:92:2: ( ( rule__Import__Group__0 ) )
            {
            // InternalShardML.g:92:2: ( ( rule__Import__Group__0 ) )
            // InternalShardML.g:93:3: ( rule__Import__Group__0 )
            {
             before(grammarAccess.getImportAccess().getGroup()); 
            // InternalShardML.g:94:3: ( rule__Import__Group__0 )
            // InternalShardML.g:94:4: rule__Import__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Import__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getImportAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleImport"


    // $ANTLR start "entryRuleDatabase"
    // InternalShardML.g:103:1: entryRuleDatabase : ruleDatabase EOF ;
    public final void entryRuleDatabase() throws RecognitionException {
        try {
            // InternalShardML.g:104:1: ( ruleDatabase EOF )
            // InternalShardML.g:105:1: ruleDatabase EOF
            {
             before(grammarAccess.getDatabaseRule()); 
            pushFollow(FOLLOW_1);
            ruleDatabase();

            state._fsp--;

             after(grammarAccess.getDatabaseRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleDatabase"


    // $ANTLR start "ruleDatabase"
    // InternalShardML.g:112:1: ruleDatabase : ( ( rule__Database__Group__0 ) ) ;
    public final void ruleDatabase() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:116:2: ( ( ( rule__Database__Group__0 ) ) )
            // InternalShardML.g:117:2: ( ( rule__Database__Group__0 ) )
            {
            // InternalShardML.g:117:2: ( ( rule__Database__Group__0 ) )
            // InternalShardML.g:118:3: ( rule__Database__Group__0 )
            {
             before(grammarAccess.getDatabaseAccess().getGroup()); 
            // InternalShardML.g:119:3: ( rule__Database__Group__0 )
            // InternalShardML.g:119:4: rule__Database__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Database__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getDatabaseAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDatabase"


    // $ANTLR start "entryRuleRelationship"
    // InternalShardML.g:128:1: entryRuleRelationship : ruleRelationship EOF ;
    public final void entryRuleRelationship() throws RecognitionException {
        try {
            // InternalShardML.g:129:1: ( ruleRelationship EOF )
            // InternalShardML.g:130:1: ruleRelationship EOF
            {
             before(grammarAccess.getRelationshipRule()); 
            pushFollow(FOLLOW_1);
            ruleRelationship();

            state._fsp--;

             after(grammarAccess.getRelationshipRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleRelationship"


    // $ANTLR start "ruleRelationship"
    // InternalShardML.g:137:1: ruleRelationship : ( ( rule__Relationship__Alternatives ) ) ;
    public final void ruleRelationship() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:141:2: ( ( ( rule__Relationship__Alternatives ) ) )
            // InternalShardML.g:142:2: ( ( rule__Relationship__Alternatives ) )
            {
            // InternalShardML.g:142:2: ( ( rule__Relationship__Alternatives ) )
            // InternalShardML.g:143:3: ( rule__Relationship__Alternatives )
            {
             before(grammarAccess.getRelationshipAccess().getAlternatives()); 
            // InternalShardML.g:144:3: ( rule__Relationship__Alternatives )
            // InternalShardML.g:144:4: rule__Relationship__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Relationship__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getRelationshipAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRelationship"


    // $ANTLR start "entryRuleHasMany"
    // InternalShardML.g:153:1: entryRuleHasMany : ruleHasMany EOF ;
    public final void entryRuleHasMany() throws RecognitionException {
        try {
            // InternalShardML.g:154:1: ( ruleHasMany EOF )
            // InternalShardML.g:155:1: ruleHasMany EOF
            {
             before(grammarAccess.getHasManyRule()); 
            pushFollow(FOLLOW_1);
            ruleHasMany();

            state._fsp--;

             after(grammarAccess.getHasManyRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleHasMany"


    // $ANTLR start "ruleHasMany"
    // InternalShardML.g:162:1: ruleHasMany : ( ( rule__HasMany__Group__0 ) ) ;
    public final void ruleHasMany() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:166:2: ( ( ( rule__HasMany__Group__0 ) ) )
            // InternalShardML.g:167:2: ( ( rule__HasMany__Group__0 ) )
            {
            // InternalShardML.g:167:2: ( ( rule__HasMany__Group__0 ) )
            // InternalShardML.g:168:3: ( rule__HasMany__Group__0 )
            {
             before(grammarAccess.getHasManyAccess().getGroup()); 
            // InternalShardML.g:169:3: ( rule__HasMany__Group__0 )
            // InternalShardML.g:169:4: rule__HasMany__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__HasMany__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getHasManyAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleHasMany"


    // $ANTLR start "entryRuleHasOne"
    // InternalShardML.g:178:1: entryRuleHasOne : ruleHasOne EOF ;
    public final void entryRuleHasOne() throws RecognitionException {
        try {
            // InternalShardML.g:179:1: ( ruleHasOne EOF )
            // InternalShardML.g:180:1: ruleHasOne EOF
            {
             before(grammarAccess.getHasOneRule()); 
            pushFollow(FOLLOW_1);
            ruleHasOne();

            state._fsp--;

             after(grammarAccess.getHasOneRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleHasOne"


    // $ANTLR start "ruleHasOne"
    // InternalShardML.g:187:1: ruleHasOne : ( ( rule__HasOne__Group__0 ) ) ;
    public final void ruleHasOne() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:191:2: ( ( ( rule__HasOne__Group__0 ) ) )
            // InternalShardML.g:192:2: ( ( rule__HasOne__Group__0 ) )
            {
            // InternalShardML.g:192:2: ( ( rule__HasOne__Group__0 ) )
            // InternalShardML.g:193:3: ( rule__HasOne__Group__0 )
            {
             before(grammarAccess.getHasOneAccess().getGroup()); 
            // InternalShardML.g:194:3: ( rule__HasOne__Group__0 )
            // InternalShardML.g:194:4: rule__HasOne__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__HasOne__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getHasOneAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleHasOne"


    // $ANTLR start "entryRuleBelongsTo"
    // InternalShardML.g:203:1: entryRuleBelongsTo : ruleBelongsTo EOF ;
    public final void entryRuleBelongsTo() throws RecognitionException {
        try {
            // InternalShardML.g:204:1: ( ruleBelongsTo EOF )
            // InternalShardML.g:205:1: ruleBelongsTo EOF
            {
             before(grammarAccess.getBelongsToRule()); 
            pushFollow(FOLLOW_1);
            ruleBelongsTo();

            state._fsp--;

             after(grammarAccess.getBelongsToRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleBelongsTo"


    // $ANTLR start "ruleBelongsTo"
    // InternalShardML.g:212:1: ruleBelongsTo : ( ( rule__BelongsTo__Group__0 ) ) ;
    public final void ruleBelongsTo() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:216:2: ( ( ( rule__BelongsTo__Group__0 ) ) )
            // InternalShardML.g:217:2: ( ( rule__BelongsTo__Group__0 ) )
            {
            // InternalShardML.g:217:2: ( ( rule__BelongsTo__Group__0 ) )
            // InternalShardML.g:218:3: ( rule__BelongsTo__Group__0 )
            {
             before(grammarAccess.getBelongsToAccess().getGroup()); 
            // InternalShardML.g:219:3: ( rule__BelongsTo__Group__0 )
            // InternalShardML.g:219:4: rule__BelongsTo__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__BelongsTo__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getBelongsToAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleBelongsTo"


    // $ANTLR start "entryRuleShardDecl"
    // InternalShardML.g:228:1: entryRuleShardDecl : ruleShardDecl EOF ;
    public final void entryRuleShardDecl() throws RecognitionException {
        try {
            // InternalShardML.g:229:1: ( ruleShardDecl EOF )
            // InternalShardML.g:230:1: ruleShardDecl EOF
            {
             before(grammarAccess.getShardDeclRule()); 
            pushFollow(FOLLOW_1);
            ruleShardDecl();

            state._fsp--;

             after(grammarAccess.getShardDeclRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleShardDecl"


    // $ANTLR start "ruleShardDecl"
    // InternalShardML.g:237:1: ruleShardDecl : ( ( rule__ShardDecl__Group__0 ) ) ;
    public final void ruleShardDecl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:241:2: ( ( ( rule__ShardDecl__Group__0 ) ) )
            // InternalShardML.g:242:2: ( ( rule__ShardDecl__Group__0 ) )
            {
            // InternalShardML.g:242:2: ( ( rule__ShardDecl__Group__0 ) )
            // InternalShardML.g:243:3: ( rule__ShardDecl__Group__0 )
            {
             before(grammarAccess.getShardDeclAccess().getGroup()); 
            // InternalShardML.g:244:3: ( rule__ShardDecl__Group__0 )
            // InternalShardML.g:244:4: rule__ShardDecl__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ShardDecl__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getShardDeclAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleShardDecl"


    // $ANTLR start "entryRuleRouteRule"
    // InternalShardML.g:253:1: entryRuleRouteRule : ruleRouteRule EOF ;
    public final void entryRuleRouteRule() throws RecognitionException {
        try {
            // InternalShardML.g:254:1: ( ruleRouteRule EOF )
            // InternalShardML.g:255:1: ruleRouteRule EOF
            {
             before(grammarAccess.getRouteRuleRule()); 
            pushFollow(FOLLOW_1);
            ruleRouteRule();

            state._fsp--;

             after(grammarAccess.getRouteRuleRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleRouteRule"


    // $ANTLR start "ruleRouteRule"
    // InternalShardML.g:262:1: ruleRouteRule : ( ( rule__RouteRule__Group__0 ) ) ;
    public final void ruleRouteRule() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:266:2: ( ( ( rule__RouteRule__Group__0 ) ) )
            // InternalShardML.g:267:2: ( ( rule__RouteRule__Group__0 ) )
            {
            // InternalShardML.g:267:2: ( ( rule__RouteRule__Group__0 ) )
            // InternalShardML.g:268:3: ( rule__RouteRule__Group__0 )
            {
             before(grammarAccess.getRouteRuleAccess().getGroup()); 
            // InternalShardML.g:269:3: ( rule__RouteRule__Group__0 )
            // InternalShardML.g:269:4: rule__RouteRule__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__RouteRule__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getRouteRuleAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRouteRule"


    // $ANTLR start "entryRuleQueryPattern"
    // InternalShardML.g:278:1: entryRuleQueryPattern : ruleQueryPattern EOF ;
    public final void entryRuleQueryPattern() throws RecognitionException {
        try {
            // InternalShardML.g:279:1: ( ruleQueryPattern EOF )
            // InternalShardML.g:280:1: ruleQueryPattern EOF
            {
             before(grammarAccess.getQueryPatternRule()); 
            pushFollow(FOLLOW_1);
            ruleQueryPattern();

            state._fsp--;

             after(grammarAccess.getQueryPatternRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleQueryPattern"


    // $ANTLR start "ruleQueryPattern"
    // InternalShardML.g:287:1: ruleQueryPattern : ( ( rule__QueryPattern__Group__0 ) ) ;
    public final void ruleQueryPattern() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:291:2: ( ( ( rule__QueryPattern__Group__0 ) ) )
            // InternalShardML.g:292:2: ( ( rule__QueryPattern__Group__0 ) )
            {
            // InternalShardML.g:292:2: ( ( rule__QueryPattern__Group__0 ) )
            // InternalShardML.g:293:3: ( rule__QueryPattern__Group__0 )
            {
             before(grammarAccess.getQueryPatternAccess().getGroup()); 
            // InternalShardML.g:294:3: ( rule__QueryPattern__Group__0 )
            // InternalShardML.g:294:4: rule__QueryPattern__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__QueryPattern__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getQueryPatternAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleQueryPattern"


    // $ANTLR start "entryRuleWhereClause"
    // InternalShardML.g:303:1: entryRuleWhereClause : ruleWhereClause EOF ;
    public final void entryRuleWhereClause() throws RecognitionException {
        try {
            // InternalShardML.g:304:1: ( ruleWhereClause EOF )
            // InternalShardML.g:305:1: ruleWhereClause EOF
            {
             before(grammarAccess.getWhereClauseRule()); 
            pushFollow(FOLLOW_1);
            ruleWhereClause();

            state._fsp--;

             after(grammarAccess.getWhereClauseRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleWhereClause"


    // $ANTLR start "ruleWhereClause"
    // InternalShardML.g:312:1: ruleWhereClause : ( ( rule__WhereClause__Group__0 ) ) ;
    public final void ruleWhereClause() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:316:2: ( ( ( rule__WhereClause__Group__0 ) ) )
            // InternalShardML.g:317:2: ( ( rule__WhereClause__Group__0 ) )
            {
            // InternalShardML.g:317:2: ( ( rule__WhereClause__Group__0 ) )
            // InternalShardML.g:318:3: ( rule__WhereClause__Group__0 )
            {
             before(grammarAccess.getWhereClauseAccess().getGroup()); 
            // InternalShardML.g:319:3: ( rule__WhereClause__Group__0 )
            // InternalShardML.g:319:4: rule__WhereClause__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__WhereClause__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getWhereClauseAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleWhereClause"


    // $ANTLR start "entryRuleWhereCondition"
    // InternalShardML.g:328:1: entryRuleWhereCondition : ruleWhereCondition EOF ;
    public final void entryRuleWhereCondition() throws RecognitionException {
        try {
            // InternalShardML.g:329:1: ( ruleWhereCondition EOF )
            // InternalShardML.g:330:1: ruleWhereCondition EOF
            {
             before(grammarAccess.getWhereConditionRule()); 
            pushFollow(FOLLOW_1);
            ruleWhereCondition();

            state._fsp--;

             after(grammarAccess.getWhereConditionRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleWhereCondition"


    // $ANTLR start "ruleWhereCondition"
    // InternalShardML.g:337:1: ruleWhereCondition : ( ( rule__WhereCondition__Group__0 ) ) ;
    public final void ruleWhereCondition() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:341:2: ( ( ( rule__WhereCondition__Group__0 ) ) )
            // InternalShardML.g:342:2: ( ( rule__WhereCondition__Group__0 ) )
            {
            // InternalShardML.g:342:2: ( ( rule__WhereCondition__Group__0 ) )
            // InternalShardML.g:343:3: ( rule__WhereCondition__Group__0 )
            {
             before(grammarAccess.getWhereConditionAccess().getGroup()); 
            // InternalShardML.g:344:3: ( rule__WhereCondition__Group__0 )
            // InternalShardML.g:344:4: rule__WhereCondition__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__WhereCondition__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getWhereConditionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleWhereCondition"


    // $ANTLR start "entryRuleSortClause"
    // InternalShardML.g:353:1: entryRuleSortClause : ruleSortClause EOF ;
    public final void entryRuleSortClause() throws RecognitionException {
        try {
            // InternalShardML.g:354:1: ( ruleSortClause EOF )
            // InternalShardML.g:355:1: ruleSortClause EOF
            {
             before(grammarAccess.getSortClauseRule()); 
            pushFollow(FOLLOW_1);
            ruleSortClause();

            state._fsp--;

             after(grammarAccess.getSortClauseRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleSortClause"


    // $ANTLR start "ruleSortClause"
    // InternalShardML.g:362:1: ruleSortClause : ( ( rule__SortClause__Group__0 ) ) ;
    public final void ruleSortClause() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:366:2: ( ( ( rule__SortClause__Group__0 ) ) )
            // InternalShardML.g:367:2: ( ( rule__SortClause__Group__0 ) )
            {
            // InternalShardML.g:367:2: ( ( rule__SortClause__Group__0 ) )
            // InternalShardML.g:368:3: ( rule__SortClause__Group__0 )
            {
             before(grammarAccess.getSortClauseAccess().getGroup()); 
            // InternalShardML.g:369:3: ( rule__SortClause__Group__0 )
            // InternalShardML.g:369:4: rule__SortClause__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__SortClause__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getSortClauseAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSortClause"


    // $ANTLR start "entryRuleGroupBy"
    // InternalShardML.g:378:1: entryRuleGroupBy : ruleGroupBy EOF ;
    public final void entryRuleGroupBy() throws RecognitionException {
        try {
            // InternalShardML.g:379:1: ( ruleGroupBy EOF )
            // InternalShardML.g:380:1: ruleGroupBy EOF
            {
             before(grammarAccess.getGroupByRule()); 
            pushFollow(FOLLOW_1);
            ruleGroupBy();

            state._fsp--;

             after(grammarAccess.getGroupByRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleGroupBy"


    // $ANTLR start "ruleGroupBy"
    // InternalShardML.g:387:1: ruleGroupBy : ( ( rule__GroupBy__FieldAssignment ) ) ;
    public final void ruleGroupBy() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:391:2: ( ( ( rule__GroupBy__FieldAssignment ) ) )
            // InternalShardML.g:392:2: ( ( rule__GroupBy__FieldAssignment ) )
            {
            // InternalShardML.g:392:2: ( ( rule__GroupBy__FieldAssignment ) )
            // InternalShardML.g:393:3: ( rule__GroupBy__FieldAssignment )
            {
             before(grammarAccess.getGroupByAccess().getFieldAssignment()); 
            // InternalShardML.g:394:3: ( rule__GroupBy__FieldAssignment )
            // InternalShardML.g:394:4: rule__GroupBy__FieldAssignment
            {
            pushFollow(FOLLOW_2);
            rule__GroupBy__FieldAssignment();

            state._fsp--;


            }

             after(grammarAccess.getGroupByAccess().getFieldAssignment()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleGroupBy"


    // $ANTLR start "ruleDatabaseType"
    // InternalShardML.g:403:1: ruleDatabaseType : ( ( rule__DatabaseType__Alternatives ) ) ;
    public final void ruleDatabaseType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:407:1: ( ( ( rule__DatabaseType__Alternatives ) ) )
            // InternalShardML.g:408:2: ( ( rule__DatabaseType__Alternatives ) )
            {
            // InternalShardML.g:408:2: ( ( rule__DatabaseType__Alternatives ) )
            // InternalShardML.g:409:3: ( rule__DatabaseType__Alternatives )
            {
             before(grammarAccess.getDatabaseTypeAccess().getAlternatives()); 
            // InternalShardML.g:410:3: ( rule__DatabaseType__Alternatives )
            // InternalShardML.g:410:4: rule__DatabaseType__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__DatabaseType__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getDatabaseTypeAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleDatabaseType"


    // $ANTLR start "ruleStrategy"
    // InternalShardML.g:419:1: ruleStrategy : ( ( rule__Strategy__Alternatives ) ) ;
    public final void ruleStrategy() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:423:1: ( ( ( rule__Strategy__Alternatives ) ) )
            // InternalShardML.g:424:2: ( ( rule__Strategy__Alternatives ) )
            {
            // InternalShardML.g:424:2: ( ( rule__Strategy__Alternatives ) )
            // InternalShardML.g:425:3: ( rule__Strategy__Alternatives )
            {
             before(grammarAccess.getStrategyAccess().getAlternatives()); 
            // InternalShardML.g:426:3: ( rule__Strategy__Alternatives )
            // InternalShardML.g:426:4: rule__Strategy__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Strategy__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getStrategyAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleStrategy"


    // $ANTLR start "rulePolicy"
    // InternalShardML.g:435:1: rulePolicy : ( ( rule__Policy__Alternatives ) ) ;
    public final void rulePolicy() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:439:1: ( ( ( rule__Policy__Alternatives ) ) )
            // InternalShardML.g:440:2: ( ( rule__Policy__Alternatives ) )
            {
            // InternalShardML.g:440:2: ( ( rule__Policy__Alternatives ) )
            // InternalShardML.g:441:3: ( rule__Policy__Alternatives )
            {
             before(grammarAccess.getPolicyAccess().getAlternatives()); 
            // InternalShardML.g:442:3: ( rule__Policy__Alternatives )
            // InternalShardML.g:442:4: rule__Policy__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Policy__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getPolicyAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePolicy"


    // $ANTLR start "ruleQueryType"
    // InternalShardML.g:451:1: ruleQueryType : ( ( rule__QueryType__Alternatives ) ) ;
    public final void ruleQueryType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:455:1: ( ( ( rule__QueryType__Alternatives ) ) )
            // InternalShardML.g:456:2: ( ( rule__QueryType__Alternatives ) )
            {
            // InternalShardML.g:456:2: ( ( rule__QueryType__Alternatives ) )
            // InternalShardML.g:457:3: ( rule__QueryType__Alternatives )
            {
             before(grammarAccess.getQueryTypeAccess().getAlternatives()); 
            // InternalShardML.g:458:3: ( rule__QueryType__Alternatives )
            // InternalShardML.g:458:4: rule__QueryType__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__QueryType__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getQueryTypeAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleQueryType"


    // $ANTLR start "ruleOperator"
    // InternalShardML.g:467:1: ruleOperator : ( ( rule__Operator__Alternatives ) ) ;
    public final void ruleOperator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:471:1: ( ( ( rule__Operator__Alternatives ) ) )
            // InternalShardML.g:472:2: ( ( rule__Operator__Alternatives ) )
            {
            // InternalShardML.g:472:2: ( ( rule__Operator__Alternatives ) )
            // InternalShardML.g:473:3: ( rule__Operator__Alternatives )
            {
             before(grammarAccess.getOperatorAccess().getAlternatives()); 
            // InternalShardML.g:474:3: ( rule__Operator__Alternatives )
            // InternalShardML.g:474:4: rule__Operator__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Operator__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getOperatorAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleOperator"


    // $ANTLR start "ruleLink"
    // InternalShardML.g:483:1: ruleLink : ( ( rule__Link__Alternatives ) ) ;
    public final void ruleLink() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:487:1: ( ( ( rule__Link__Alternatives ) ) )
            // InternalShardML.g:488:2: ( ( rule__Link__Alternatives ) )
            {
            // InternalShardML.g:488:2: ( ( rule__Link__Alternatives ) )
            // InternalShardML.g:489:3: ( rule__Link__Alternatives )
            {
             before(grammarAccess.getLinkAccess().getAlternatives()); 
            // InternalShardML.g:490:3: ( rule__Link__Alternatives )
            // InternalShardML.g:490:4: rule__Link__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Link__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getLinkAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLink"


    // $ANTLR start "ruleSortOrder"
    // InternalShardML.g:499:1: ruleSortOrder : ( ( rule__SortOrder__Alternatives ) ) ;
    public final void ruleSortOrder() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:503:1: ( ( ( rule__SortOrder__Alternatives ) ) )
            // InternalShardML.g:504:2: ( ( rule__SortOrder__Alternatives ) )
            {
            // InternalShardML.g:504:2: ( ( rule__SortOrder__Alternatives ) )
            // InternalShardML.g:505:3: ( rule__SortOrder__Alternatives )
            {
             before(grammarAccess.getSortOrderAccess().getAlternatives()); 
            // InternalShardML.g:506:3: ( rule__SortOrder__Alternatives )
            // InternalShardML.g:506:4: rule__SortOrder__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__SortOrder__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getSortOrderAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSortOrder"


    // $ANTLR start "rule__Relationship__Alternatives"
    // InternalShardML.g:514:1: rule__Relationship__Alternatives : ( ( ruleHasMany ) | ( ruleHasOne ) | ( ruleBelongsTo ) );
    public final void rule__Relationship__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:518:1: ( ( ruleHasMany ) | ( ruleHasOne ) | ( ruleBelongsTo ) )
            int alt1=3;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==RULE_ID) ) {
                switch ( input.LA(2) ) {
                case 38:
                    {
                    alt1=2;
                    }
                    break;
                case 39:
                    {
                    alt1=3;
                    }
                    break;
                case 37:
                    {
                    alt1=1;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 1, 1, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // InternalShardML.g:519:2: ( ruleHasMany )
                    {
                    // InternalShardML.g:519:2: ( ruleHasMany )
                    // InternalShardML.g:520:3: ruleHasMany
                    {
                     before(grammarAccess.getRelationshipAccess().getHasManyParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleHasMany();

                    state._fsp--;

                     after(grammarAccess.getRelationshipAccess().getHasManyParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:525:2: ( ruleHasOne )
                    {
                    // InternalShardML.g:525:2: ( ruleHasOne )
                    // InternalShardML.g:526:3: ruleHasOne
                    {
                     before(grammarAccess.getRelationshipAccess().getHasOneParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    ruleHasOne();

                    state._fsp--;

                     after(grammarAccess.getRelationshipAccess().getHasOneParserRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalShardML.g:531:2: ( ruleBelongsTo )
                    {
                    // InternalShardML.g:531:2: ( ruleBelongsTo )
                    // InternalShardML.g:532:3: ruleBelongsTo
                    {
                     before(grammarAccess.getRelationshipAccess().getBelongsToParserRuleCall_2()); 
                    pushFollow(FOLLOW_2);
                    ruleBelongsTo();

                    state._fsp--;

                     after(grammarAccess.getRelationshipAccess().getBelongsToParserRuleCall_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Relationship__Alternatives"


    // $ANTLR start "rule__DatabaseType__Alternatives"
    // InternalShardML.g:541:1: rule__DatabaseType__Alternatives : ( ( ( 'postgres' ) ) | ( ( 'mysql' ) ) );
    public final void rule__DatabaseType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:545:1: ( ( ( 'postgres' ) ) | ( ( 'mysql' ) ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==11) ) {
                alt2=1;
            }
            else if ( (LA2_0==12) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // InternalShardML.g:546:2: ( ( 'postgres' ) )
                    {
                    // InternalShardML.g:546:2: ( ( 'postgres' ) )
                    // InternalShardML.g:547:3: ( 'postgres' )
                    {
                     before(grammarAccess.getDatabaseTypeAccess().getPOSTGRESEnumLiteralDeclaration_0()); 
                    // InternalShardML.g:548:3: ( 'postgres' )
                    // InternalShardML.g:548:4: 'postgres'
                    {
                    match(input,11,FOLLOW_2); 

                    }

                     after(grammarAccess.getDatabaseTypeAccess().getPOSTGRESEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:552:2: ( ( 'mysql' ) )
                    {
                    // InternalShardML.g:552:2: ( ( 'mysql' ) )
                    // InternalShardML.g:553:3: ( 'mysql' )
                    {
                     before(grammarAccess.getDatabaseTypeAccess().getMYSQLEnumLiteralDeclaration_1()); 
                    // InternalShardML.g:554:3: ( 'mysql' )
                    // InternalShardML.g:554:4: 'mysql'
                    {
                    match(input,12,FOLLOW_2); 

                    }

                     after(grammarAccess.getDatabaseTypeAccess().getMYSQLEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__DatabaseType__Alternatives"


    // $ANTLR start "rule__Strategy__Alternatives"
    // InternalShardML.g:562:1: rule__Strategy__Alternatives : ( ( ( 'hash' ) ) | ( ( 'consistent_hash' ) ) | ( ( 'replicated' ) ) );
    public final void rule__Strategy__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:566:1: ( ( ( 'hash' ) ) | ( ( 'consistent_hash' ) ) | ( ( 'replicated' ) ) )
            int alt3=3;
            switch ( input.LA(1) ) {
            case 13:
                {
                alt3=1;
                }
                break;
            case 14:
                {
                alt3=2;
                }
                break;
            case 15:
                {
                alt3=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // InternalShardML.g:567:2: ( ( 'hash' ) )
                    {
                    // InternalShardML.g:567:2: ( ( 'hash' ) )
                    // InternalShardML.g:568:3: ( 'hash' )
                    {
                     before(grammarAccess.getStrategyAccess().getHASHEnumLiteralDeclaration_0()); 
                    // InternalShardML.g:569:3: ( 'hash' )
                    // InternalShardML.g:569:4: 'hash'
                    {
                    match(input,13,FOLLOW_2); 

                    }

                     after(grammarAccess.getStrategyAccess().getHASHEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:573:2: ( ( 'consistent_hash' ) )
                    {
                    // InternalShardML.g:573:2: ( ( 'consistent_hash' ) )
                    // InternalShardML.g:574:3: ( 'consistent_hash' )
                    {
                     before(grammarAccess.getStrategyAccess().getCONS_HASHEnumLiteralDeclaration_1()); 
                    // InternalShardML.g:575:3: ( 'consistent_hash' )
                    // InternalShardML.g:575:4: 'consistent_hash'
                    {
                    match(input,14,FOLLOW_2); 

                    }

                     after(grammarAccess.getStrategyAccess().getCONS_HASHEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalShardML.g:579:2: ( ( 'replicated' ) )
                    {
                    // InternalShardML.g:579:2: ( ( 'replicated' ) )
                    // InternalShardML.g:580:3: ( 'replicated' )
                    {
                     before(grammarAccess.getStrategyAccess().getREPLICATEDEnumLiteralDeclaration_2()); 
                    // InternalShardML.g:581:3: ( 'replicated' )
                    // InternalShardML.g:581:4: 'replicated'
                    {
                    match(input,15,FOLLOW_2); 

                    }

                     after(grammarAccess.getStrategyAccess().getREPLICATEDEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Strategy__Alternatives"


    // $ANTLR start "rule__Policy__Alternatives"
    // InternalShardML.g:589:1: rule__Policy__Alternatives : ( ( ( 'allow' ) ) | ( ( 'warn' ) ) | ( ( 'deny' ) ) );
    public final void rule__Policy__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:593:1: ( ( ( 'allow' ) ) | ( ( 'warn' ) ) | ( ( 'deny' ) ) )
            int alt4=3;
            switch ( input.LA(1) ) {
            case 16:
                {
                alt4=1;
                }
                break;
            case 17:
                {
                alt4=2;
                }
                break;
            case 18:
                {
                alt4=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // InternalShardML.g:594:2: ( ( 'allow' ) )
                    {
                    // InternalShardML.g:594:2: ( ( 'allow' ) )
                    // InternalShardML.g:595:3: ( 'allow' )
                    {
                     before(grammarAccess.getPolicyAccess().getALLOWEnumLiteralDeclaration_0()); 
                    // InternalShardML.g:596:3: ( 'allow' )
                    // InternalShardML.g:596:4: 'allow'
                    {
                    match(input,16,FOLLOW_2); 

                    }

                     after(grammarAccess.getPolicyAccess().getALLOWEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:600:2: ( ( 'warn' ) )
                    {
                    // InternalShardML.g:600:2: ( ( 'warn' ) )
                    // InternalShardML.g:601:3: ( 'warn' )
                    {
                     before(grammarAccess.getPolicyAccess().getWARNEnumLiteralDeclaration_1()); 
                    // InternalShardML.g:602:3: ( 'warn' )
                    // InternalShardML.g:602:4: 'warn'
                    {
                    match(input,17,FOLLOW_2); 

                    }

                     after(grammarAccess.getPolicyAccess().getWARNEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalShardML.g:606:2: ( ( 'deny' ) )
                    {
                    // InternalShardML.g:606:2: ( ( 'deny' ) )
                    // InternalShardML.g:607:3: ( 'deny' )
                    {
                     before(grammarAccess.getPolicyAccess().getDENYEnumLiteralDeclaration_2()); 
                    // InternalShardML.g:608:3: ( 'deny' )
                    // InternalShardML.g:608:4: 'deny'
                    {
                    match(input,18,FOLLOW_2); 

                    }

                     after(grammarAccess.getPolicyAccess().getDENYEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Policy__Alternatives"


    // $ANTLR start "rule__QueryType__Alternatives"
    // InternalShardML.g:616:1: rule__QueryType__Alternatives : ( ( ( 'read' ) ) | ( ( 'write' ) ) | ( ( 'delete' ) ) );
    public final void rule__QueryType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:620:1: ( ( ( 'read' ) ) | ( ( 'write' ) ) | ( ( 'delete' ) ) )
            int alt5=3;
            switch ( input.LA(1) ) {
            case 19:
                {
                alt5=1;
                }
                break;
            case 20:
                {
                alt5=2;
                }
                break;
            case 21:
                {
                alt5=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // InternalShardML.g:621:2: ( ( 'read' ) )
                    {
                    // InternalShardML.g:621:2: ( ( 'read' ) )
                    // InternalShardML.g:622:3: ( 'read' )
                    {
                     before(grammarAccess.getQueryTypeAccess().getREADEnumLiteralDeclaration_0()); 
                    // InternalShardML.g:623:3: ( 'read' )
                    // InternalShardML.g:623:4: 'read'
                    {
                    match(input,19,FOLLOW_2); 

                    }

                     after(grammarAccess.getQueryTypeAccess().getREADEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:627:2: ( ( 'write' ) )
                    {
                    // InternalShardML.g:627:2: ( ( 'write' ) )
                    // InternalShardML.g:628:3: ( 'write' )
                    {
                     before(grammarAccess.getQueryTypeAccess().getWRITEEnumLiteralDeclaration_1()); 
                    // InternalShardML.g:629:3: ( 'write' )
                    // InternalShardML.g:629:4: 'write'
                    {
                    match(input,20,FOLLOW_2); 

                    }

                     after(grammarAccess.getQueryTypeAccess().getWRITEEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalShardML.g:633:2: ( ( 'delete' ) )
                    {
                    // InternalShardML.g:633:2: ( ( 'delete' ) )
                    // InternalShardML.g:634:3: ( 'delete' )
                    {
                     before(grammarAccess.getQueryTypeAccess().getDELETEEnumLiteralDeclaration_2()); 
                    // InternalShardML.g:635:3: ( 'delete' )
                    // InternalShardML.g:635:4: 'delete'
                    {
                    match(input,21,FOLLOW_2); 

                    }

                     after(grammarAccess.getQueryTypeAccess().getDELETEEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryType__Alternatives"


    // $ANTLR start "rule__Operator__Alternatives"
    // InternalShardML.g:643:1: rule__Operator__Alternatives : ( ( ( '=' ) ) | ( ( '>' ) ) | ( ( '<' ) ) | ( ( '<=' ) ) | ( ( '>=' ) ) | ( ( '!=' ) ) );
    public final void rule__Operator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:647:1: ( ( ( '=' ) ) | ( ( '>' ) ) | ( ( '<' ) ) | ( ( '<=' ) ) | ( ( '>=' ) ) | ( ( '!=' ) ) )
            int alt6=6;
            switch ( input.LA(1) ) {
            case 22:
                {
                alt6=1;
                }
                break;
            case 23:
                {
                alt6=2;
                }
                break;
            case 24:
                {
                alt6=3;
                }
                break;
            case 25:
                {
                alt6=4;
                }
                break;
            case 26:
                {
                alt6=5;
                }
                break;
            case 27:
                {
                alt6=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // InternalShardML.g:648:2: ( ( '=' ) )
                    {
                    // InternalShardML.g:648:2: ( ( '=' ) )
                    // InternalShardML.g:649:3: ( '=' )
                    {
                     before(grammarAccess.getOperatorAccess().getEQEnumLiteralDeclaration_0()); 
                    // InternalShardML.g:650:3: ( '=' )
                    // InternalShardML.g:650:4: '='
                    {
                    match(input,22,FOLLOW_2); 

                    }

                     after(grammarAccess.getOperatorAccess().getEQEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:654:2: ( ( '>' ) )
                    {
                    // InternalShardML.g:654:2: ( ( '>' ) )
                    // InternalShardML.g:655:3: ( '>' )
                    {
                     before(grammarAccess.getOperatorAccess().getGEEnumLiteralDeclaration_1()); 
                    // InternalShardML.g:656:3: ( '>' )
                    // InternalShardML.g:656:4: '>'
                    {
                    match(input,23,FOLLOW_2); 

                    }

                     after(grammarAccess.getOperatorAccess().getGEEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalShardML.g:660:2: ( ( '<' ) )
                    {
                    // InternalShardML.g:660:2: ( ( '<' ) )
                    // InternalShardML.g:661:3: ( '<' )
                    {
                     before(grammarAccess.getOperatorAccess().getLEEnumLiteralDeclaration_2()); 
                    // InternalShardML.g:662:3: ( '<' )
                    // InternalShardML.g:662:4: '<'
                    {
                    match(input,24,FOLLOW_2); 

                    }

                     after(grammarAccess.getOperatorAccess().getLEEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalShardML.g:666:2: ( ( '<=' ) )
                    {
                    // InternalShardML.g:666:2: ( ( '<=' ) )
                    // InternalShardML.g:667:3: ( '<=' )
                    {
                     before(grammarAccess.getOperatorAccess().getLEQEnumLiteralDeclaration_3()); 
                    // InternalShardML.g:668:3: ( '<=' )
                    // InternalShardML.g:668:4: '<='
                    {
                    match(input,25,FOLLOW_2); 

                    }

                     after(grammarAccess.getOperatorAccess().getLEQEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalShardML.g:672:2: ( ( '>=' ) )
                    {
                    // InternalShardML.g:672:2: ( ( '>=' ) )
                    // InternalShardML.g:673:3: ( '>=' )
                    {
                     before(grammarAccess.getOperatorAccess().getGEQEnumLiteralDeclaration_4()); 
                    // InternalShardML.g:674:3: ( '>=' )
                    // InternalShardML.g:674:4: '>='
                    {
                    match(input,26,FOLLOW_2); 

                    }

                     after(grammarAccess.getOperatorAccess().getGEQEnumLiteralDeclaration_4()); 

                    }


                    }
                    break;
                case 6 :
                    // InternalShardML.g:678:2: ( ( '!=' ) )
                    {
                    // InternalShardML.g:678:2: ( ( '!=' ) )
                    // InternalShardML.g:679:3: ( '!=' )
                    {
                     before(grammarAccess.getOperatorAccess().getNEQEnumLiteralDeclaration_5()); 
                    // InternalShardML.g:680:3: ( '!=' )
                    // InternalShardML.g:680:4: '!='
                    {
                    match(input,27,FOLLOW_2); 

                    }

                     after(grammarAccess.getOperatorAccess().getNEQEnumLiteralDeclaration_5()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Operator__Alternatives"


    // $ANTLR start "rule__Link__Alternatives"
    // InternalShardML.g:688:1: rule__Link__Alternatives : ( ( ( 'AND' ) ) | ( ( 'OR' ) ) );
    public final void rule__Link__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:692:1: ( ( ( 'AND' ) ) | ( ( 'OR' ) ) )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==28) ) {
                alt7=1;
            }
            else if ( (LA7_0==29) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // InternalShardML.g:693:2: ( ( 'AND' ) )
                    {
                    // InternalShardML.g:693:2: ( ( 'AND' ) )
                    // InternalShardML.g:694:3: ( 'AND' )
                    {
                     before(grammarAccess.getLinkAccess().getANDEnumLiteralDeclaration_0()); 
                    // InternalShardML.g:695:3: ( 'AND' )
                    // InternalShardML.g:695:4: 'AND'
                    {
                    match(input,28,FOLLOW_2); 

                    }

                     after(grammarAccess.getLinkAccess().getANDEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:699:2: ( ( 'OR' ) )
                    {
                    // InternalShardML.g:699:2: ( ( 'OR' ) )
                    // InternalShardML.g:700:3: ( 'OR' )
                    {
                     before(grammarAccess.getLinkAccess().getOREnumLiteralDeclaration_1()); 
                    // InternalShardML.g:701:3: ( 'OR' )
                    // InternalShardML.g:701:4: 'OR'
                    {
                    match(input,29,FOLLOW_2); 

                    }

                     after(grammarAccess.getLinkAccess().getOREnumLiteralDeclaration_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Alternatives"


    // $ANTLR start "rule__SortOrder__Alternatives"
    // InternalShardML.g:709:1: rule__SortOrder__Alternatives : ( ( ( 'ASC' ) ) | ( ( 'DESC' ) ) );
    public final void rule__SortOrder__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:713:1: ( ( ( 'ASC' ) ) | ( ( 'DESC' ) ) )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==30) ) {
                alt8=1;
            }
            else if ( (LA8_0==31) ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // InternalShardML.g:714:2: ( ( 'ASC' ) )
                    {
                    // InternalShardML.g:714:2: ( ( 'ASC' ) )
                    // InternalShardML.g:715:3: ( 'ASC' )
                    {
                     before(grammarAccess.getSortOrderAccess().getASCEnumLiteralDeclaration_0()); 
                    // InternalShardML.g:716:3: ( 'ASC' )
                    // InternalShardML.g:716:4: 'ASC'
                    {
                    match(input,30,FOLLOW_2); 

                    }

                     after(grammarAccess.getSortOrderAccess().getASCEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:720:2: ( ( 'DESC' ) )
                    {
                    // InternalShardML.g:720:2: ( ( 'DESC' ) )
                    // InternalShardML.g:721:3: ( 'DESC' )
                    {
                     before(grammarAccess.getSortOrderAccess().getDESCEnumLiteralDeclaration_1()); 
                    // InternalShardML.g:722:3: ( 'DESC' )
                    // InternalShardML.g:722:4: 'DESC'
                    {
                    match(input,31,FOLLOW_2); 

                    }

                     after(grammarAccess.getSortOrderAccess().getDESCEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SortOrder__Alternatives"


    // $ANTLR start "rule__Model__Group__0"
    // InternalShardML.g:730:1: rule__Model__Group__0 : rule__Model__Group__0__Impl rule__Model__Group__1 ;
    public final void rule__Model__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:734:1: ( rule__Model__Group__0__Impl rule__Model__Group__1 )
            // InternalShardML.g:735:2: rule__Model__Group__0__Impl rule__Model__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__Model__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Model__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__0"


    // $ANTLR start "rule__Model__Group__0__Impl"
    // InternalShardML.g:742:1: rule__Model__Group__0__Impl : ( ( rule__Model__ImportsAssignment_0 )* ) ;
    public final void rule__Model__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:746:1: ( ( ( rule__Model__ImportsAssignment_0 )* ) )
            // InternalShardML.g:747:1: ( ( rule__Model__ImportsAssignment_0 )* )
            {
            // InternalShardML.g:747:1: ( ( rule__Model__ImportsAssignment_0 )* )
            // InternalShardML.g:748:2: ( rule__Model__ImportsAssignment_0 )*
            {
             before(grammarAccess.getModelAccess().getImportsAssignment_0()); 
            // InternalShardML.g:749:2: ( rule__Model__ImportsAssignment_0 )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==32) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalShardML.g:749:3: rule__Model__ImportsAssignment_0
            	    {
            	    pushFollow(FOLLOW_4);
            	    rule__Model__ImportsAssignment_0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

             after(grammarAccess.getModelAccess().getImportsAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__0__Impl"


    // $ANTLR start "rule__Model__Group__1"
    // InternalShardML.g:757:1: rule__Model__Group__1 : rule__Model__Group__1__Impl ;
    public final void rule__Model__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:761:1: ( rule__Model__Group__1__Impl )
            // InternalShardML.g:762:2: rule__Model__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Model__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__1"


    // $ANTLR start "rule__Model__Group__1__Impl"
    // InternalShardML.g:768:1: rule__Model__Group__1__Impl : ( ( rule__Model__DbAssignment_1 ) ) ;
    public final void rule__Model__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:772:1: ( ( ( rule__Model__DbAssignment_1 ) ) )
            // InternalShardML.g:773:1: ( ( rule__Model__DbAssignment_1 ) )
            {
            // InternalShardML.g:773:1: ( ( rule__Model__DbAssignment_1 ) )
            // InternalShardML.g:774:2: ( rule__Model__DbAssignment_1 )
            {
             before(grammarAccess.getModelAccess().getDbAssignment_1()); 
            // InternalShardML.g:775:2: ( rule__Model__DbAssignment_1 )
            // InternalShardML.g:775:3: rule__Model__DbAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Model__DbAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getModelAccess().getDbAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__1__Impl"


    // $ANTLR start "rule__Import__Group__0"
    // InternalShardML.g:784:1: rule__Import__Group__0 : rule__Import__Group__0__Impl rule__Import__Group__1 ;
    public final void rule__Import__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:788:1: ( rule__Import__Group__0__Impl rule__Import__Group__1 )
            // InternalShardML.g:789:2: rule__Import__Group__0__Impl rule__Import__Group__1
            {
            pushFollow(FOLLOW_5);
            rule__Import__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Import__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Import__Group__0"


    // $ANTLR start "rule__Import__Group__0__Impl"
    // InternalShardML.g:796:1: rule__Import__Group__0__Impl : ( 'import' ) ;
    public final void rule__Import__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:800:1: ( ( 'import' ) )
            // InternalShardML.g:801:1: ( 'import' )
            {
            // InternalShardML.g:801:1: ( 'import' )
            // InternalShardML.g:802:2: 'import'
            {
             before(grammarAccess.getImportAccess().getImportKeyword_0()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getImportAccess().getImportKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Import__Group__0__Impl"


    // $ANTLR start "rule__Import__Group__1"
    // InternalShardML.g:811:1: rule__Import__Group__1 : rule__Import__Group__1__Impl ;
    public final void rule__Import__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:815:1: ( rule__Import__Group__1__Impl )
            // InternalShardML.g:816:2: rule__Import__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Import__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Import__Group__1"


    // $ANTLR start "rule__Import__Group__1__Impl"
    // InternalShardML.g:822:1: rule__Import__Group__1__Impl : ( ( rule__Import__ImportURIAssignment_1 ) ) ;
    public final void rule__Import__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:826:1: ( ( ( rule__Import__ImportURIAssignment_1 ) ) )
            // InternalShardML.g:827:1: ( ( rule__Import__ImportURIAssignment_1 ) )
            {
            // InternalShardML.g:827:1: ( ( rule__Import__ImportURIAssignment_1 ) )
            // InternalShardML.g:828:2: ( rule__Import__ImportURIAssignment_1 )
            {
             before(grammarAccess.getImportAccess().getImportURIAssignment_1()); 
            // InternalShardML.g:829:2: ( rule__Import__ImportURIAssignment_1 )
            // InternalShardML.g:829:3: rule__Import__ImportURIAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Import__ImportURIAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getImportAccess().getImportURIAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Import__Group__1__Impl"


    // $ANTLR start "rule__Database__Group__0"
    // InternalShardML.g:838:1: rule__Database__Group__0 : rule__Database__Group__0__Impl rule__Database__Group__1 ;
    public final void rule__Database__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:842:1: ( rule__Database__Group__0__Impl rule__Database__Group__1 )
            // InternalShardML.g:843:2: rule__Database__Group__0__Impl rule__Database__Group__1
            {
            pushFollow(FOLLOW_6);
            rule__Database__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Database__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__Group__0"


    // $ANTLR start "rule__Database__Group__0__Impl"
    // InternalShardML.g:850:1: rule__Database__Group__0__Impl : ( 'database' ) ;
    public final void rule__Database__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:854:1: ( ( 'database' ) )
            // InternalShardML.g:855:1: ( 'database' )
            {
            // InternalShardML.g:855:1: ( 'database' )
            // InternalShardML.g:856:2: 'database'
            {
             before(grammarAccess.getDatabaseAccess().getDatabaseKeyword_0()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getDatabaseAccess().getDatabaseKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__Group__0__Impl"


    // $ANTLR start "rule__Database__Group__1"
    // InternalShardML.g:865:1: rule__Database__Group__1 : rule__Database__Group__1__Impl rule__Database__Group__2 ;
    public final void rule__Database__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:869:1: ( rule__Database__Group__1__Impl rule__Database__Group__2 )
            // InternalShardML.g:870:2: rule__Database__Group__1__Impl rule__Database__Group__2
            {
            pushFollow(FOLLOW_7);
            rule__Database__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Database__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__Group__1"


    // $ANTLR start "rule__Database__Group__1__Impl"
    // InternalShardML.g:877:1: rule__Database__Group__1__Impl : ( ( rule__Database__NameAssignment_1 ) ) ;
    public final void rule__Database__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:881:1: ( ( ( rule__Database__NameAssignment_1 ) ) )
            // InternalShardML.g:882:1: ( ( rule__Database__NameAssignment_1 ) )
            {
            // InternalShardML.g:882:1: ( ( rule__Database__NameAssignment_1 ) )
            // InternalShardML.g:883:2: ( rule__Database__NameAssignment_1 )
            {
             before(grammarAccess.getDatabaseAccess().getNameAssignment_1()); 
            // InternalShardML.g:884:2: ( rule__Database__NameAssignment_1 )
            // InternalShardML.g:884:3: rule__Database__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Database__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getDatabaseAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__Group__1__Impl"


    // $ANTLR start "rule__Database__Group__2"
    // InternalShardML.g:892:1: rule__Database__Group__2 : rule__Database__Group__2__Impl rule__Database__Group__3 ;
    public final void rule__Database__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:896:1: ( rule__Database__Group__2__Impl rule__Database__Group__3 )
            // InternalShardML.g:897:2: rule__Database__Group__2__Impl rule__Database__Group__3
            {
            pushFollow(FOLLOW_8);
            rule__Database__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Database__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__Group__2"


    // $ANTLR start "rule__Database__Group__2__Impl"
    // InternalShardML.g:904:1: rule__Database__Group__2__Impl : ( '{' ) ;
    public final void rule__Database__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:908:1: ( ( '{' ) )
            // InternalShardML.g:909:1: ( '{' )
            {
            // InternalShardML.g:909:1: ( '{' )
            // InternalShardML.g:910:2: '{'
            {
             before(grammarAccess.getDatabaseAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,34,FOLLOW_2); 
             after(grammarAccess.getDatabaseAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__Group__2__Impl"


    // $ANTLR start "rule__Database__Group__3"
    // InternalShardML.g:919:1: rule__Database__Group__3 : rule__Database__Group__3__Impl rule__Database__Group__4 ;
    public final void rule__Database__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:923:1: ( rule__Database__Group__3__Impl rule__Database__Group__4 )
            // InternalShardML.g:924:2: rule__Database__Group__3__Impl rule__Database__Group__4
            {
            pushFollow(FOLLOW_9);
            rule__Database__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Database__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__Group__3"


    // $ANTLR start "rule__Database__Group__3__Impl"
    // InternalShardML.g:931:1: rule__Database__Group__3__Impl : ( 'type:' ) ;
    public final void rule__Database__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:935:1: ( ( 'type:' ) )
            // InternalShardML.g:936:1: ( 'type:' )
            {
            // InternalShardML.g:936:1: ( 'type:' )
            // InternalShardML.g:937:2: 'type:'
            {
             before(grammarAccess.getDatabaseAccess().getTypeKeyword_3()); 
            match(input,35,FOLLOW_2); 
             after(grammarAccess.getDatabaseAccess().getTypeKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__Group__3__Impl"


    // $ANTLR start "rule__Database__Group__4"
    // InternalShardML.g:946:1: rule__Database__Group__4 : rule__Database__Group__4__Impl rule__Database__Group__5 ;
    public final void rule__Database__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:950:1: ( rule__Database__Group__4__Impl rule__Database__Group__5 )
            // InternalShardML.g:951:2: rule__Database__Group__4__Impl rule__Database__Group__5
            {
            pushFollow(FOLLOW_10);
            rule__Database__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Database__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__Group__4"


    // $ANTLR start "rule__Database__Group__4__Impl"
    // InternalShardML.g:958:1: rule__Database__Group__4__Impl : ( ( rule__Database__TypeAssignment_4 ) ) ;
    public final void rule__Database__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:962:1: ( ( ( rule__Database__TypeAssignment_4 ) ) )
            // InternalShardML.g:963:1: ( ( rule__Database__TypeAssignment_4 ) )
            {
            // InternalShardML.g:963:1: ( ( rule__Database__TypeAssignment_4 ) )
            // InternalShardML.g:964:2: ( rule__Database__TypeAssignment_4 )
            {
             before(grammarAccess.getDatabaseAccess().getTypeAssignment_4()); 
            // InternalShardML.g:965:2: ( rule__Database__TypeAssignment_4 )
            // InternalShardML.g:965:3: rule__Database__TypeAssignment_4
            {
            pushFollow(FOLLOW_2);
            rule__Database__TypeAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getDatabaseAccess().getTypeAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__Group__4__Impl"


    // $ANTLR start "rule__Database__Group__5"
    // InternalShardML.g:973:1: rule__Database__Group__5 : rule__Database__Group__5__Impl rule__Database__Group__6 ;
    public final void rule__Database__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:977:1: ( rule__Database__Group__5__Impl rule__Database__Group__6 )
            // InternalShardML.g:978:2: rule__Database__Group__5__Impl rule__Database__Group__6
            {
            pushFollow(FOLLOW_10);
            rule__Database__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Database__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__Group__5"


    // $ANTLR start "rule__Database__Group__5__Impl"
    // InternalShardML.g:985:1: rule__Database__Group__5__Impl : ( ( rule__Database__Shard_declAssignment_5 )* ) ;
    public final void rule__Database__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:989:1: ( ( ( rule__Database__Shard_declAssignment_5 )* ) )
            // InternalShardML.g:990:1: ( ( rule__Database__Shard_declAssignment_5 )* )
            {
            // InternalShardML.g:990:1: ( ( rule__Database__Shard_declAssignment_5 )* )
            // InternalShardML.g:991:2: ( rule__Database__Shard_declAssignment_5 )*
            {
             before(grammarAccess.getDatabaseAccess().getShard_declAssignment_5()); 
            // InternalShardML.g:992:2: ( rule__Database__Shard_declAssignment_5 )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==40) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalShardML.g:992:3: rule__Database__Shard_declAssignment_5
            	    {
            	    pushFollow(FOLLOW_11);
            	    rule__Database__Shard_declAssignment_5();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

             after(grammarAccess.getDatabaseAccess().getShard_declAssignment_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__Group__5__Impl"


    // $ANTLR start "rule__Database__Group__6"
    // InternalShardML.g:1000:1: rule__Database__Group__6 : rule__Database__Group__6__Impl rule__Database__Group__7 ;
    public final void rule__Database__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1004:1: ( rule__Database__Group__6__Impl rule__Database__Group__7 )
            // InternalShardML.g:1005:2: rule__Database__Group__6__Impl rule__Database__Group__7
            {
            pushFollow(FOLLOW_10);
            rule__Database__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Database__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__Group__6"


    // $ANTLR start "rule__Database__Group__6__Impl"
    // InternalShardML.g:1012:1: rule__Database__Group__6__Impl : ( ( rule__Database__Route_rulesAssignment_6 )* ) ;
    public final void rule__Database__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1016:1: ( ( ( rule__Database__Route_rulesAssignment_6 )* ) )
            // InternalShardML.g:1017:1: ( ( rule__Database__Route_rulesAssignment_6 )* )
            {
            // InternalShardML.g:1017:1: ( ( rule__Database__Route_rulesAssignment_6 )* )
            // InternalShardML.g:1018:2: ( rule__Database__Route_rulesAssignment_6 )*
            {
             before(grammarAccess.getDatabaseAccess().getRoute_rulesAssignment_6()); 
            // InternalShardML.g:1019:2: ( rule__Database__Route_rulesAssignment_6 )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==45) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalShardML.g:1019:3: rule__Database__Route_rulesAssignment_6
            	    {
            	    pushFollow(FOLLOW_12);
            	    rule__Database__Route_rulesAssignment_6();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

             after(grammarAccess.getDatabaseAccess().getRoute_rulesAssignment_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__Group__6__Impl"


    // $ANTLR start "rule__Database__Group__7"
    // InternalShardML.g:1027:1: rule__Database__Group__7 : rule__Database__Group__7__Impl rule__Database__Group__8 ;
    public final void rule__Database__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1031:1: ( rule__Database__Group__7__Impl rule__Database__Group__8 )
            // InternalShardML.g:1032:2: rule__Database__Group__7__Impl rule__Database__Group__8
            {
            pushFollow(FOLLOW_10);
            rule__Database__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Database__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__Group__7"


    // $ANTLR start "rule__Database__Group__7__Impl"
    // InternalShardML.g:1039:1: rule__Database__Group__7__Impl : ( ( rule__Database__RelationshipsAssignment_7 )* ) ;
    public final void rule__Database__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1043:1: ( ( ( rule__Database__RelationshipsAssignment_7 )* ) )
            // InternalShardML.g:1044:1: ( ( rule__Database__RelationshipsAssignment_7 )* )
            {
            // InternalShardML.g:1044:1: ( ( rule__Database__RelationshipsAssignment_7 )* )
            // InternalShardML.g:1045:2: ( rule__Database__RelationshipsAssignment_7 )*
            {
             before(grammarAccess.getDatabaseAccess().getRelationshipsAssignment_7()); 
            // InternalShardML.g:1046:2: ( rule__Database__RelationshipsAssignment_7 )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==RULE_ID) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalShardML.g:1046:3: rule__Database__RelationshipsAssignment_7
            	    {
            	    pushFollow(FOLLOW_13);
            	    rule__Database__RelationshipsAssignment_7();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

             after(grammarAccess.getDatabaseAccess().getRelationshipsAssignment_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__Group__7__Impl"


    // $ANTLR start "rule__Database__Group__8"
    // InternalShardML.g:1054:1: rule__Database__Group__8 : rule__Database__Group__8__Impl ;
    public final void rule__Database__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1058:1: ( rule__Database__Group__8__Impl )
            // InternalShardML.g:1059:2: rule__Database__Group__8__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Database__Group__8__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__Group__8"


    // $ANTLR start "rule__Database__Group__8__Impl"
    // InternalShardML.g:1065:1: rule__Database__Group__8__Impl : ( '}' ) ;
    public final void rule__Database__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1069:1: ( ( '}' ) )
            // InternalShardML.g:1070:1: ( '}' )
            {
            // InternalShardML.g:1070:1: ( '}' )
            // InternalShardML.g:1071:2: '}'
            {
             before(grammarAccess.getDatabaseAccess().getRightCurlyBracketKeyword_8()); 
            match(input,36,FOLLOW_2); 
             after(grammarAccess.getDatabaseAccess().getRightCurlyBracketKeyword_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__Group__8__Impl"


    // $ANTLR start "rule__HasMany__Group__0"
    // InternalShardML.g:1081:1: rule__HasMany__Group__0 : rule__HasMany__Group__0__Impl rule__HasMany__Group__1 ;
    public final void rule__HasMany__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1085:1: ( rule__HasMany__Group__0__Impl rule__HasMany__Group__1 )
            // InternalShardML.g:1086:2: rule__HasMany__Group__0__Impl rule__HasMany__Group__1
            {
            pushFollow(FOLLOW_14);
            rule__HasMany__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__HasMany__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HasMany__Group__0"


    // $ANTLR start "rule__HasMany__Group__0__Impl"
    // InternalShardML.g:1093:1: rule__HasMany__Group__0__Impl : ( ( rule__HasMany__SubjectAssignment_0 ) ) ;
    public final void rule__HasMany__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1097:1: ( ( ( rule__HasMany__SubjectAssignment_0 ) ) )
            // InternalShardML.g:1098:1: ( ( rule__HasMany__SubjectAssignment_0 ) )
            {
            // InternalShardML.g:1098:1: ( ( rule__HasMany__SubjectAssignment_0 ) )
            // InternalShardML.g:1099:2: ( rule__HasMany__SubjectAssignment_0 )
            {
             before(grammarAccess.getHasManyAccess().getSubjectAssignment_0()); 
            // InternalShardML.g:1100:2: ( rule__HasMany__SubjectAssignment_0 )
            // InternalShardML.g:1100:3: rule__HasMany__SubjectAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__HasMany__SubjectAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getHasManyAccess().getSubjectAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HasMany__Group__0__Impl"


    // $ANTLR start "rule__HasMany__Group__1"
    // InternalShardML.g:1108:1: rule__HasMany__Group__1 : rule__HasMany__Group__1__Impl rule__HasMany__Group__2 ;
    public final void rule__HasMany__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1112:1: ( rule__HasMany__Group__1__Impl rule__HasMany__Group__2 )
            // InternalShardML.g:1113:2: rule__HasMany__Group__1__Impl rule__HasMany__Group__2
            {
            pushFollow(FOLLOW_6);
            rule__HasMany__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__HasMany__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HasMany__Group__1"


    // $ANTLR start "rule__HasMany__Group__1__Impl"
    // InternalShardML.g:1120:1: rule__HasMany__Group__1__Impl : ( 'has_many' ) ;
    public final void rule__HasMany__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1124:1: ( ( 'has_many' ) )
            // InternalShardML.g:1125:1: ( 'has_many' )
            {
            // InternalShardML.g:1125:1: ( 'has_many' )
            // InternalShardML.g:1126:2: 'has_many'
            {
             before(grammarAccess.getHasManyAccess().getHas_manyKeyword_1()); 
            match(input,37,FOLLOW_2); 
             after(grammarAccess.getHasManyAccess().getHas_manyKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HasMany__Group__1__Impl"


    // $ANTLR start "rule__HasMany__Group__2"
    // InternalShardML.g:1135:1: rule__HasMany__Group__2 : rule__HasMany__Group__2__Impl ;
    public final void rule__HasMany__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1139:1: ( rule__HasMany__Group__2__Impl )
            // InternalShardML.g:1140:2: rule__HasMany__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__HasMany__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HasMany__Group__2"


    // $ANTLR start "rule__HasMany__Group__2__Impl"
    // InternalShardML.g:1146:1: rule__HasMany__Group__2__Impl : ( ( rule__HasMany__ManyAssignment_2 ) ) ;
    public final void rule__HasMany__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1150:1: ( ( ( rule__HasMany__ManyAssignment_2 ) ) )
            // InternalShardML.g:1151:1: ( ( rule__HasMany__ManyAssignment_2 ) )
            {
            // InternalShardML.g:1151:1: ( ( rule__HasMany__ManyAssignment_2 ) )
            // InternalShardML.g:1152:2: ( rule__HasMany__ManyAssignment_2 )
            {
             before(grammarAccess.getHasManyAccess().getManyAssignment_2()); 
            // InternalShardML.g:1153:2: ( rule__HasMany__ManyAssignment_2 )
            // InternalShardML.g:1153:3: rule__HasMany__ManyAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__HasMany__ManyAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getHasManyAccess().getManyAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HasMany__Group__2__Impl"


    // $ANTLR start "rule__HasOne__Group__0"
    // InternalShardML.g:1162:1: rule__HasOne__Group__0 : rule__HasOne__Group__0__Impl rule__HasOne__Group__1 ;
    public final void rule__HasOne__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1166:1: ( rule__HasOne__Group__0__Impl rule__HasOne__Group__1 )
            // InternalShardML.g:1167:2: rule__HasOne__Group__0__Impl rule__HasOne__Group__1
            {
            pushFollow(FOLLOW_15);
            rule__HasOne__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__HasOne__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HasOne__Group__0"


    // $ANTLR start "rule__HasOne__Group__0__Impl"
    // InternalShardML.g:1174:1: rule__HasOne__Group__0__Impl : ( ( rule__HasOne__SubjectAssignment_0 ) ) ;
    public final void rule__HasOne__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1178:1: ( ( ( rule__HasOne__SubjectAssignment_0 ) ) )
            // InternalShardML.g:1179:1: ( ( rule__HasOne__SubjectAssignment_0 ) )
            {
            // InternalShardML.g:1179:1: ( ( rule__HasOne__SubjectAssignment_0 ) )
            // InternalShardML.g:1180:2: ( rule__HasOne__SubjectAssignment_0 )
            {
             before(grammarAccess.getHasOneAccess().getSubjectAssignment_0()); 
            // InternalShardML.g:1181:2: ( rule__HasOne__SubjectAssignment_0 )
            // InternalShardML.g:1181:3: rule__HasOne__SubjectAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__HasOne__SubjectAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getHasOneAccess().getSubjectAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HasOne__Group__0__Impl"


    // $ANTLR start "rule__HasOne__Group__1"
    // InternalShardML.g:1189:1: rule__HasOne__Group__1 : rule__HasOne__Group__1__Impl rule__HasOne__Group__2 ;
    public final void rule__HasOne__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1193:1: ( rule__HasOne__Group__1__Impl rule__HasOne__Group__2 )
            // InternalShardML.g:1194:2: rule__HasOne__Group__1__Impl rule__HasOne__Group__2
            {
            pushFollow(FOLLOW_6);
            rule__HasOne__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__HasOne__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HasOne__Group__1"


    // $ANTLR start "rule__HasOne__Group__1__Impl"
    // InternalShardML.g:1201:1: rule__HasOne__Group__1__Impl : ( 'has_one' ) ;
    public final void rule__HasOne__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1205:1: ( ( 'has_one' ) )
            // InternalShardML.g:1206:1: ( 'has_one' )
            {
            // InternalShardML.g:1206:1: ( 'has_one' )
            // InternalShardML.g:1207:2: 'has_one'
            {
             before(grammarAccess.getHasOneAccess().getHas_oneKeyword_1()); 
            match(input,38,FOLLOW_2); 
             after(grammarAccess.getHasOneAccess().getHas_oneKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HasOne__Group__1__Impl"


    // $ANTLR start "rule__HasOne__Group__2"
    // InternalShardML.g:1216:1: rule__HasOne__Group__2 : rule__HasOne__Group__2__Impl ;
    public final void rule__HasOne__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1220:1: ( rule__HasOne__Group__2__Impl )
            // InternalShardML.g:1221:2: rule__HasOne__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__HasOne__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HasOne__Group__2"


    // $ANTLR start "rule__HasOne__Group__2__Impl"
    // InternalShardML.g:1227:1: rule__HasOne__Group__2__Impl : ( ( rule__HasOne__OneAssignment_2 ) ) ;
    public final void rule__HasOne__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1231:1: ( ( ( rule__HasOne__OneAssignment_2 ) ) )
            // InternalShardML.g:1232:1: ( ( rule__HasOne__OneAssignment_2 ) )
            {
            // InternalShardML.g:1232:1: ( ( rule__HasOne__OneAssignment_2 ) )
            // InternalShardML.g:1233:2: ( rule__HasOne__OneAssignment_2 )
            {
             before(grammarAccess.getHasOneAccess().getOneAssignment_2()); 
            // InternalShardML.g:1234:2: ( rule__HasOne__OneAssignment_2 )
            // InternalShardML.g:1234:3: rule__HasOne__OneAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__HasOne__OneAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getHasOneAccess().getOneAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HasOne__Group__2__Impl"


    // $ANTLR start "rule__BelongsTo__Group__0"
    // InternalShardML.g:1243:1: rule__BelongsTo__Group__0 : rule__BelongsTo__Group__0__Impl rule__BelongsTo__Group__1 ;
    public final void rule__BelongsTo__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1247:1: ( rule__BelongsTo__Group__0__Impl rule__BelongsTo__Group__1 )
            // InternalShardML.g:1248:2: rule__BelongsTo__Group__0__Impl rule__BelongsTo__Group__1
            {
            pushFollow(FOLLOW_16);
            rule__BelongsTo__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__BelongsTo__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BelongsTo__Group__0"


    // $ANTLR start "rule__BelongsTo__Group__0__Impl"
    // InternalShardML.g:1255:1: rule__BelongsTo__Group__0__Impl : ( ( rule__BelongsTo__SubjectAssignment_0 ) ) ;
    public final void rule__BelongsTo__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1259:1: ( ( ( rule__BelongsTo__SubjectAssignment_0 ) ) )
            // InternalShardML.g:1260:1: ( ( rule__BelongsTo__SubjectAssignment_0 ) )
            {
            // InternalShardML.g:1260:1: ( ( rule__BelongsTo__SubjectAssignment_0 ) )
            // InternalShardML.g:1261:2: ( rule__BelongsTo__SubjectAssignment_0 )
            {
             before(grammarAccess.getBelongsToAccess().getSubjectAssignment_0()); 
            // InternalShardML.g:1262:2: ( rule__BelongsTo__SubjectAssignment_0 )
            // InternalShardML.g:1262:3: rule__BelongsTo__SubjectAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__BelongsTo__SubjectAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getBelongsToAccess().getSubjectAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BelongsTo__Group__0__Impl"


    // $ANTLR start "rule__BelongsTo__Group__1"
    // InternalShardML.g:1270:1: rule__BelongsTo__Group__1 : rule__BelongsTo__Group__1__Impl rule__BelongsTo__Group__2 ;
    public final void rule__BelongsTo__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1274:1: ( rule__BelongsTo__Group__1__Impl rule__BelongsTo__Group__2 )
            // InternalShardML.g:1275:2: rule__BelongsTo__Group__1__Impl rule__BelongsTo__Group__2
            {
            pushFollow(FOLLOW_6);
            rule__BelongsTo__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__BelongsTo__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BelongsTo__Group__1"


    // $ANTLR start "rule__BelongsTo__Group__1__Impl"
    // InternalShardML.g:1282:1: rule__BelongsTo__Group__1__Impl : ( 'belongs_to' ) ;
    public final void rule__BelongsTo__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1286:1: ( ( 'belongs_to' ) )
            // InternalShardML.g:1287:1: ( 'belongs_to' )
            {
            // InternalShardML.g:1287:1: ( 'belongs_to' )
            // InternalShardML.g:1288:2: 'belongs_to'
            {
             before(grammarAccess.getBelongsToAccess().getBelongs_toKeyword_1()); 
            match(input,39,FOLLOW_2); 
             after(grammarAccess.getBelongsToAccess().getBelongs_toKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BelongsTo__Group__1__Impl"


    // $ANTLR start "rule__BelongsTo__Group__2"
    // InternalShardML.g:1297:1: rule__BelongsTo__Group__2 : rule__BelongsTo__Group__2__Impl ;
    public final void rule__BelongsTo__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1301:1: ( rule__BelongsTo__Group__2__Impl )
            // InternalShardML.g:1302:2: rule__BelongsTo__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__BelongsTo__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BelongsTo__Group__2"


    // $ANTLR start "rule__BelongsTo__Group__2__Impl"
    // InternalShardML.g:1308:1: rule__BelongsTo__Group__2__Impl : ( ( rule__BelongsTo__BelongsAssignment_2 ) ) ;
    public final void rule__BelongsTo__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1312:1: ( ( ( rule__BelongsTo__BelongsAssignment_2 ) ) )
            // InternalShardML.g:1313:1: ( ( rule__BelongsTo__BelongsAssignment_2 ) )
            {
            // InternalShardML.g:1313:1: ( ( rule__BelongsTo__BelongsAssignment_2 ) )
            // InternalShardML.g:1314:2: ( rule__BelongsTo__BelongsAssignment_2 )
            {
             before(grammarAccess.getBelongsToAccess().getBelongsAssignment_2()); 
            // InternalShardML.g:1315:2: ( rule__BelongsTo__BelongsAssignment_2 )
            // InternalShardML.g:1315:3: rule__BelongsTo__BelongsAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__BelongsTo__BelongsAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getBelongsToAccess().getBelongsAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BelongsTo__Group__2__Impl"


    // $ANTLR start "rule__ShardDecl__Group__0"
    // InternalShardML.g:1324:1: rule__ShardDecl__Group__0 : rule__ShardDecl__Group__0__Impl rule__ShardDecl__Group__1 ;
    public final void rule__ShardDecl__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1328:1: ( rule__ShardDecl__Group__0__Impl rule__ShardDecl__Group__1 )
            // InternalShardML.g:1329:2: rule__ShardDecl__Group__0__Impl rule__ShardDecl__Group__1
            {
            pushFollow(FOLLOW_6);
            rule__ShardDecl__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ShardDecl__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group__0"


    // $ANTLR start "rule__ShardDecl__Group__0__Impl"
    // InternalShardML.g:1336:1: rule__ShardDecl__Group__0__Impl : ( 'shard' ) ;
    public final void rule__ShardDecl__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1340:1: ( ( 'shard' ) )
            // InternalShardML.g:1341:1: ( 'shard' )
            {
            // InternalShardML.g:1341:1: ( 'shard' )
            // InternalShardML.g:1342:2: 'shard'
            {
             before(grammarAccess.getShardDeclAccess().getShardKeyword_0()); 
            match(input,40,FOLLOW_2); 
             after(grammarAccess.getShardDeclAccess().getShardKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group__0__Impl"


    // $ANTLR start "rule__ShardDecl__Group__1"
    // InternalShardML.g:1351:1: rule__ShardDecl__Group__1 : rule__ShardDecl__Group__1__Impl rule__ShardDecl__Group__2 ;
    public final void rule__ShardDecl__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1355:1: ( rule__ShardDecl__Group__1__Impl rule__ShardDecl__Group__2 )
            // InternalShardML.g:1356:2: rule__ShardDecl__Group__1__Impl rule__ShardDecl__Group__2
            {
            pushFollow(FOLLOW_7);
            rule__ShardDecl__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ShardDecl__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group__1"


    // $ANTLR start "rule__ShardDecl__Group__1__Impl"
    // InternalShardML.g:1363:1: rule__ShardDecl__Group__1__Impl : ( ( rule__ShardDecl__EntityAssignment_1 ) ) ;
    public final void rule__ShardDecl__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1367:1: ( ( ( rule__ShardDecl__EntityAssignment_1 ) ) )
            // InternalShardML.g:1368:1: ( ( rule__ShardDecl__EntityAssignment_1 ) )
            {
            // InternalShardML.g:1368:1: ( ( rule__ShardDecl__EntityAssignment_1 ) )
            // InternalShardML.g:1369:2: ( rule__ShardDecl__EntityAssignment_1 )
            {
             before(grammarAccess.getShardDeclAccess().getEntityAssignment_1()); 
            // InternalShardML.g:1370:2: ( rule__ShardDecl__EntityAssignment_1 )
            // InternalShardML.g:1370:3: rule__ShardDecl__EntityAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__ShardDecl__EntityAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getShardDeclAccess().getEntityAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group__1__Impl"


    // $ANTLR start "rule__ShardDecl__Group__2"
    // InternalShardML.g:1378:1: rule__ShardDecl__Group__2 : rule__ShardDecl__Group__2__Impl rule__ShardDecl__Group__3 ;
    public final void rule__ShardDecl__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1382:1: ( rule__ShardDecl__Group__2__Impl rule__ShardDecl__Group__3 )
            // InternalShardML.g:1383:2: rule__ShardDecl__Group__2__Impl rule__ShardDecl__Group__3
            {
            pushFollow(FOLLOW_17);
            rule__ShardDecl__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ShardDecl__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group__2"


    // $ANTLR start "rule__ShardDecl__Group__2__Impl"
    // InternalShardML.g:1390:1: rule__ShardDecl__Group__2__Impl : ( '{' ) ;
    public final void rule__ShardDecl__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1394:1: ( ( '{' ) )
            // InternalShardML.g:1395:1: ( '{' )
            {
            // InternalShardML.g:1395:1: ( '{' )
            // InternalShardML.g:1396:2: '{'
            {
             before(grammarAccess.getShardDeclAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,34,FOLLOW_2); 
             after(grammarAccess.getShardDeclAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group__2__Impl"


    // $ANTLR start "rule__ShardDecl__Group__3"
    // InternalShardML.g:1405:1: rule__ShardDecl__Group__3 : rule__ShardDecl__Group__3__Impl rule__ShardDecl__Group__4 ;
    public final void rule__ShardDecl__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1409:1: ( rule__ShardDecl__Group__3__Impl rule__ShardDecl__Group__4 )
            // InternalShardML.g:1410:2: rule__ShardDecl__Group__3__Impl rule__ShardDecl__Group__4
            {
            pushFollow(FOLLOW_18);
            rule__ShardDecl__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ShardDecl__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group__3"


    // $ANTLR start "rule__ShardDecl__Group__3__Impl"
    // InternalShardML.g:1417:1: rule__ShardDecl__Group__3__Impl : ( 'strategy:' ) ;
    public final void rule__ShardDecl__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1421:1: ( ( 'strategy:' ) )
            // InternalShardML.g:1422:1: ( 'strategy:' )
            {
            // InternalShardML.g:1422:1: ( 'strategy:' )
            // InternalShardML.g:1423:2: 'strategy:'
            {
             before(grammarAccess.getShardDeclAccess().getStrategyKeyword_3()); 
            match(input,41,FOLLOW_2); 
             after(grammarAccess.getShardDeclAccess().getStrategyKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group__3__Impl"


    // $ANTLR start "rule__ShardDecl__Group__4"
    // InternalShardML.g:1432:1: rule__ShardDecl__Group__4 : rule__ShardDecl__Group__4__Impl rule__ShardDecl__Group__5 ;
    public final void rule__ShardDecl__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1436:1: ( rule__ShardDecl__Group__4__Impl rule__ShardDecl__Group__5 )
            // InternalShardML.g:1437:2: rule__ShardDecl__Group__4__Impl rule__ShardDecl__Group__5
            {
            pushFollow(FOLLOW_19);
            rule__ShardDecl__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ShardDecl__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group__4"


    // $ANTLR start "rule__ShardDecl__Group__4__Impl"
    // InternalShardML.g:1444:1: rule__ShardDecl__Group__4__Impl : ( ( rule__ShardDecl__StrategyAssignment_4 ) ) ;
    public final void rule__ShardDecl__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1448:1: ( ( ( rule__ShardDecl__StrategyAssignment_4 ) ) )
            // InternalShardML.g:1449:1: ( ( rule__ShardDecl__StrategyAssignment_4 ) )
            {
            // InternalShardML.g:1449:1: ( ( rule__ShardDecl__StrategyAssignment_4 ) )
            // InternalShardML.g:1450:2: ( rule__ShardDecl__StrategyAssignment_4 )
            {
             before(grammarAccess.getShardDeclAccess().getStrategyAssignment_4()); 
            // InternalShardML.g:1451:2: ( rule__ShardDecl__StrategyAssignment_4 )
            // InternalShardML.g:1451:3: rule__ShardDecl__StrategyAssignment_4
            {
            pushFollow(FOLLOW_2);
            rule__ShardDecl__StrategyAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getShardDeclAccess().getStrategyAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group__4__Impl"


    // $ANTLR start "rule__ShardDecl__Group__5"
    // InternalShardML.g:1459:1: rule__ShardDecl__Group__5 : rule__ShardDecl__Group__5__Impl rule__ShardDecl__Group__6 ;
    public final void rule__ShardDecl__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1463:1: ( rule__ShardDecl__Group__5__Impl rule__ShardDecl__Group__6 )
            // InternalShardML.g:1464:2: rule__ShardDecl__Group__5__Impl rule__ShardDecl__Group__6
            {
            pushFollow(FOLLOW_19);
            rule__ShardDecl__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ShardDecl__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group__5"


    // $ANTLR start "rule__ShardDecl__Group__5__Impl"
    // InternalShardML.g:1471:1: rule__ShardDecl__Group__5__Impl : ( ( rule__ShardDecl__Group_5__0 )? ) ;
    public final void rule__ShardDecl__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1475:1: ( ( ( rule__ShardDecl__Group_5__0 )? ) )
            // InternalShardML.g:1476:1: ( ( rule__ShardDecl__Group_5__0 )? )
            {
            // InternalShardML.g:1476:1: ( ( rule__ShardDecl__Group_5__0 )? )
            // InternalShardML.g:1477:2: ( rule__ShardDecl__Group_5__0 )?
            {
             before(grammarAccess.getShardDeclAccess().getGroup_5()); 
            // InternalShardML.g:1478:2: ( rule__ShardDecl__Group_5__0 )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==42) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalShardML.g:1478:3: rule__ShardDecl__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ShardDecl__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getShardDeclAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group__5__Impl"


    // $ANTLR start "rule__ShardDecl__Group__6"
    // InternalShardML.g:1486:1: rule__ShardDecl__Group__6 : rule__ShardDecl__Group__6__Impl rule__ShardDecl__Group__7 ;
    public final void rule__ShardDecl__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1490:1: ( rule__ShardDecl__Group__6__Impl rule__ShardDecl__Group__7 )
            // InternalShardML.g:1491:2: rule__ShardDecl__Group__6__Impl rule__ShardDecl__Group__7
            {
            pushFollow(FOLLOW_19);
            rule__ShardDecl__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ShardDecl__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group__6"


    // $ANTLR start "rule__ShardDecl__Group__6__Impl"
    // InternalShardML.g:1498:1: rule__ShardDecl__Group__6__Impl : ( ( rule__ShardDecl__Group_6__0 )? ) ;
    public final void rule__ShardDecl__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1502:1: ( ( ( rule__ShardDecl__Group_6__0 )? ) )
            // InternalShardML.g:1503:1: ( ( rule__ShardDecl__Group_6__0 )? )
            {
            // InternalShardML.g:1503:1: ( ( rule__ShardDecl__Group_6__0 )? )
            // InternalShardML.g:1504:2: ( rule__ShardDecl__Group_6__0 )?
            {
             before(grammarAccess.getShardDeclAccess().getGroup_6()); 
            // InternalShardML.g:1505:2: ( rule__ShardDecl__Group_6__0 )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==43) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalShardML.g:1505:3: rule__ShardDecl__Group_6__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ShardDecl__Group_6__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getShardDeclAccess().getGroup_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group__6__Impl"


    // $ANTLR start "rule__ShardDecl__Group__7"
    // InternalShardML.g:1513:1: rule__ShardDecl__Group__7 : rule__ShardDecl__Group__7__Impl rule__ShardDecl__Group__8 ;
    public final void rule__ShardDecl__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1517:1: ( rule__ShardDecl__Group__7__Impl rule__ShardDecl__Group__8 )
            // InternalShardML.g:1518:2: rule__ShardDecl__Group__7__Impl rule__ShardDecl__Group__8
            {
            pushFollow(FOLLOW_19);
            rule__ShardDecl__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ShardDecl__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group__7"


    // $ANTLR start "rule__ShardDecl__Group__7__Impl"
    // InternalShardML.g:1525:1: rule__ShardDecl__Group__7__Impl : ( ( rule__ShardDecl__Group_7__0 )? ) ;
    public final void rule__ShardDecl__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1529:1: ( ( ( rule__ShardDecl__Group_7__0 )? ) )
            // InternalShardML.g:1530:1: ( ( rule__ShardDecl__Group_7__0 )? )
            {
            // InternalShardML.g:1530:1: ( ( rule__ShardDecl__Group_7__0 )? )
            // InternalShardML.g:1531:2: ( rule__ShardDecl__Group_7__0 )?
            {
             before(grammarAccess.getShardDeclAccess().getGroup_7()); 
            // InternalShardML.g:1532:2: ( rule__ShardDecl__Group_7__0 )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==44) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalShardML.g:1532:3: rule__ShardDecl__Group_7__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ShardDecl__Group_7__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getShardDeclAccess().getGroup_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group__7__Impl"


    // $ANTLR start "rule__ShardDecl__Group__8"
    // InternalShardML.g:1540:1: rule__ShardDecl__Group__8 : rule__ShardDecl__Group__8__Impl ;
    public final void rule__ShardDecl__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1544:1: ( rule__ShardDecl__Group__8__Impl )
            // InternalShardML.g:1545:2: rule__ShardDecl__Group__8__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ShardDecl__Group__8__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group__8"


    // $ANTLR start "rule__ShardDecl__Group__8__Impl"
    // InternalShardML.g:1551:1: rule__ShardDecl__Group__8__Impl : ( '}' ) ;
    public final void rule__ShardDecl__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1555:1: ( ( '}' ) )
            // InternalShardML.g:1556:1: ( '}' )
            {
            // InternalShardML.g:1556:1: ( '}' )
            // InternalShardML.g:1557:2: '}'
            {
             before(grammarAccess.getShardDeclAccess().getRightCurlyBracketKeyword_8()); 
            match(input,36,FOLLOW_2); 
             after(grammarAccess.getShardDeclAccess().getRightCurlyBracketKeyword_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group__8__Impl"


    // $ANTLR start "rule__ShardDecl__Group_5__0"
    // InternalShardML.g:1567:1: rule__ShardDecl__Group_5__0 : rule__ShardDecl__Group_5__0__Impl rule__ShardDecl__Group_5__1 ;
    public final void rule__ShardDecl__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1571:1: ( rule__ShardDecl__Group_5__0__Impl rule__ShardDecl__Group_5__1 )
            // InternalShardML.g:1572:2: rule__ShardDecl__Group_5__0__Impl rule__ShardDecl__Group_5__1
            {
            pushFollow(FOLLOW_6);
            rule__ShardDecl__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ShardDecl__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group_5__0"


    // $ANTLR start "rule__ShardDecl__Group_5__0__Impl"
    // InternalShardML.g:1579:1: rule__ShardDecl__Group_5__0__Impl : ( 'key:' ) ;
    public final void rule__ShardDecl__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1583:1: ( ( 'key:' ) )
            // InternalShardML.g:1584:1: ( 'key:' )
            {
            // InternalShardML.g:1584:1: ( 'key:' )
            // InternalShardML.g:1585:2: 'key:'
            {
             before(grammarAccess.getShardDeclAccess().getKeyKeyword_5_0()); 
            match(input,42,FOLLOW_2); 
             after(grammarAccess.getShardDeclAccess().getKeyKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group_5__0__Impl"


    // $ANTLR start "rule__ShardDecl__Group_5__1"
    // InternalShardML.g:1594:1: rule__ShardDecl__Group_5__1 : rule__ShardDecl__Group_5__1__Impl ;
    public final void rule__ShardDecl__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1598:1: ( rule__ShardDecl__Group_5__1__Impl )
            // InternalShardML.g:1599:2: rule__ShardDecl__Group_5__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ShardDecl__Group_5__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group_5__1"


    // $ANTLR start "rule__ShardDecl__Group_5__1__Impl"
    // InternalShardML.g:1605:1: rule__ShardDecl__Group_5__1__Impl : ( ( rule__ShardDecl__IdAssignment_5_1 ) ) ;
    public final void rule__ShardDecl__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1609:1: ( ( ( rule__ShardDecl__IdAssignment_5_1 ) ) )
            // InternalShardML.g:1610:1: ( ( rule__ShardDecl__IdAssignment_5_1 ) )
            {
            // InternalShardML.g:1610:1: ( ( rule__ShardDecl__IdAssignment_5_1 ) )
            // InternalShardML.g:1611:2: ( rule__ShardDecl__IdAssignment_5_1 )
            {
             before(grammarAccess.getShardDeclAccess().getIdAssignment_5_1()); 
            // InternalShardML.g:1612:2: ( rule__ShardDecl__IdAssignment_5_1 )
            // InternalShardML.g:1612:3: rule__ShardDecl__IdAssignment_5_1
            {
            pushFollow(FOLLOW_2);
            rule__ShardDecl__IdAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getShardDeclAccess().getIdAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group_5__1__Impl"


    // $ANTLR start "rule__ShardDecl__Group_6__0"
    // InternalShardML.g:1621:1: rule__ShardDecl__Group_6__0 : rule__ShardDecl__Group_6__0__Impl rule__ShardDecl__Group_6__1 ;
    public final void rule__ShardDecl__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1625:1: ( rule__ShardDecl__Group_6__0__Impl rule__ShardDecl__Group_6__1 )
            // InternalShardML.g:1626:2: rule__ShardDecl__Group_6__0__Impl rule__ShardDecl__Group_6__1
            {
            pushFollow(FOLLOW_20);
            rule__ShardDecl__Group_6__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ShardDecl__Group_6__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group_6__0"


    // $ANTLR start "rule__ShardDecl__Group_6__0__Impl"
    // InternalShardML.g:1633:1: rule__ShardDecl__Group_6__0__Impl : ( 'buckets:' ) ;
    public final void rule__ShardDecl__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1637:1: ( ( 'buckets:' ) )
            // InternalShardML.g:1638:1: ( 'buckets:' )
            {
            // InternalShardML.g:1638:1: ( 'buckets:' )
            // InternalShardML.g:1639:2: 'buckets:'
            {
             before(grammarAccess.getShardDeclAccess().getBucketsKeyword_6_0()); 
            match(input,43,FOLLOW_2); 
             after(grammarAccess.getShardDeclAccess().getBucketsKeyword_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group_6__0__Impl"


    // $ANTLR start "rule__ShardDecl__Group_6__1"
    // InternalShardML.g:1648:1: rule__ShardDecl__Group_6__1 : rule__ShardDecl__Group_6__1__Impl ;
    public final void rule__ShardDecl__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1652:1: ( rule__ShardDecl__Group_6__1__Impl )
            // InternalShardML.g:1653:2: rule__ShardDecl__Group_6__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ShardDecl__Group_6__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group_6__1"


    // $ANTLR start "rule__ShardDecl__Group_6__1__Impl"
    // InternalShardML.g:1659:1: rule__ShardDecl__Group_6__1__Impl : ( ( rule__ShardDecl__BucketsAssignment_6_1 ) ) ;
    public final void rule__ShardDecl__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1663:1: ( ( ( rule__ShardDecl__BucketsAssignment_6_1 ) ) )
            // InternalShardML.g:1664:1: ( ( rule__ShardDecl__BucketsAssignment_6_1 ) )
            {
            // InternalShardML.g:1664:1: ( ( rule__ShardDecl__BucketsAssignment_6_1 ) )
            // InternalShardML.g:1665:2: ( rule__ShardDecl__BucketsAssignment_6_1 )
            {
             before(grammarAccess.getShardDeclAccess().getBucketsAssignment_6_1()); 
            // InternalShardML.g:1666:2: ( rule__ShardDecl__BucketsAssignment_6_1 )
            // InternalShardML.g:1666:3: rule__ShardDecl__BucketsAssignment_6_1
            {
            pushFollow(FOLLOW_2);
            rule__ShardDecl__BucketsAssignment_6_1();

            state._fsp--;


            }

             after(grammarAccess.getShardDeclAccess().getBucketsAssignment_6_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group_6__1__Impl"


    // $ANTLR start "rule__ShardDecl__Group_7__0"
    // InternalShardML.g:1675:1: rule__ShardDecl__Group_7__0 : rule__ShardDecl__Group_7__0__Impl rule__ShardDecl__Group_7__1 ;
    public final void rule__ShardDecl__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1679:1: ( rule__ShardDecl__Group_7__0__Impl rule__ShardDecl__Group_7__1 )
            // InternalShardML.g:1680:2: rule__ShardDecl__Group_7__0__Impl rule__ShardDecl__Group_7__1
            {
            pushFollow(FOLLOW_6);
            rule__ShardDecl__Group_7__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ShardDecl__Group_7__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group_7__0"


    // $ANTLR start "rule__ShardDecl__Group_7__0__Impl"
    // InternalShardML.g:1687:1: rule__ShardDecl__Group_7__0__Impl : ( 'colocate_with:' ) ;
    public final void rule__ShardDecl__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1691:1: ( ( 'colocate_with:' ) )
            // InternalShardML.g:1692:1: ( 'colocate_with:' )
            {
            // InternalShardML.g:1692:1: ( 'colocate_with:' )
            // InternalShardML.g:1693:2: 'colocate_with:'
            {
             before(grammarAccess.getShardDeclAccess().getColocate_withKeyword_7_0()); 
            match(input,44,FOLLOW_2); 
             after(grammarAccess.getShardDeclAccess().getColocate_withKeyword_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group_7__0__Impl"


    // $ANTLR start "rule__ShardDecl__Group_7__1"
    // InternalShardML.g:1702:1: rule__ShardDecl__Group_7__1 : rule__ShardDecl__Group_7__1__Impl ;
    public final void rule__ShardDecl__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1706:1: ( rule__ShardDecl__Group_7__1__Impl )
            // InternalShardML.g:1707:2: rule__ShardDecl__Group_7__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ShardDecl__Group_7__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group_7__1"


    // $ANTLR start "rule__ShardDecl__Group_7__1__Impl"
    // InternalShardML.g:1713:1: rule__ShardDecl__Group_7__1__Impl : ( ( rule__ShardDecl__ColocateAssignment_7_1 ) ) ;
    public final void rule__ShardDecl__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1717:1: ( ( ( rule__ShardDecl__ColocateAssignment_7_1 ) ) )
            // InternalShardML.g:1718:1: ( ( rule__ShardDecl__ColocateAssignment_7_1 ) )
            {
            // InternalShardML.g:1718:1: ( ( rule__ShardDecl__ColocateAssignment_7_1 ) )
            // InternalShardML.g:1719:2: ( rule__ShardDecl__ColocateAssignment_7_1 )
            {
             before(grammarAccess.getShardDeclAccess().getColocateAssignment_7_1()); 
            // InternalShardML.g:1720:2: ( rule__ShardDecl__ColocateAssignment_7_1 )
            // InternalShardML.g:1720:3: rule__ShardDecl__ColocateAssignment_7_1
            {
            pushFollow(FOLLOW_2);
            rule__ShardDecl__ColocateAssignment_7_1();

            state._fsp--;


            }

             after(grammarAccess.getShardDeclAccess().getColocateAssignment_7_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__Group_7__1__Impl"


    // $ANTLR start "rule__RouteRule__Group__0"
    // InternalShardML.g:1729:1: rule__RouteRule__Group__0 : rule__RouteRule__Group__0__Impl rule__RouteRule__Group__1 ;
    public final void rule__RouteRule__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1733:1: ( rule__RouteRule__Group__0__Impl rule__RouteRule__Group__1 )
            // InternalShardML.g:1734:2: rule__RouteRule__Group__0__Impl rule__RouteRule__Group__1
            {
            pushFollow(FOLLOW_6);
            rule__RouteRule__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RouteRule__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__Group__0"


    // $ANTLR start "rule__RouteRule__Group__0__Impl"
    // InternalShardML.g:1741:1: rule__RouteRule__Group__0__Impl : ( 'route' ) ;
    public final void rule__RouteRule__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1745:1: ( ( 'route' ) )
            // InternalShardML.g:1746:1: ( 'route' )
            {
            // InternalShardML.g:1746:1: ( 'route' )
            // InternalShardML.g:1747:2: 'route'
            {
             before(grammarAccess.getRouteRuleAccess().getRouteKeyword_0()); 
            match(input,45,FOLLOW_2); 
             after(grammarAccess.getRouteRuleAccess().getRouteKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__Group__0__Impl"


    // $ANTLR start "rule__RouteRule__Group__1"
    // InternalShardML.g:1756:1: rule__RouteRule__Group__1 : rule__RouteRule__Group__1__Impl rule__RouteRule__Group__2 ;
    public final void rule__RouteRule__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1760:1: ( rule__RouteRule__Group__1__Impl rule__RouteRule__Group__2 )
            // InternalShardML.g:1761:2: rule__RouteRule__Group__1__Impl rule__RouteRule__Group__2
            {
            pushFollow(FOLLOW_7);
            rule__RouteRule__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RouteRule__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__Group__1"


    // $ANTLR start "rule__RouteRule__Group__1__Impl"
    // InternalShardML.g:1768:1: rule__RouteRule__Group__1__Impl : ( ( rule__RouteRule__EntityAssignment_1 ) ) ;
    public final void rule__RouteRule__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1772:1: ( ( ( rule__RouteRule__EntityAssignment_1 ) ) )
            // InternalShardML.g:1773:1: ( ( rule__RouteRule__EntityAssignment_1 ) )
            {
            // InternalShardML.g:1773:1: ( ( rule__RouteRule__EntityAssignment_1 ) )
            // InternalShardML.g:1774:2: ( rule__RouteRule__EntityAssignment_1 )
            {
             before(grammarAccess.getRouteRuleAccess().getEntityAssignment_1()); 
            // InternalShardML.g:1775:2: ( rule__RouteRule__EntityAssignment_1 )
            // InternalShardML.g:1775:3: rule__RouteRule__EntityAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__RouteRule__EntityAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getRouteRuleAccess().getEntityAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__Group__1__Impl"


    // $ANTLR start "rule__RouteRule__Group__2"
    // InternalShardML.g:1783:1: rule__RouteRule__Group__2 : rule__RouteRule__Group__2__Impl rule__RouteRule__Group__3 ;
    public final void rule__RouteRule__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1787:1: ( rule__RouteRule__Group__2__Impl rule__RouteRule__Group__3 )
            // InternalShardML.g:1788:2: rule__RouteRule__Group__2__Impl rule__RouteRule__Group__3
            {
            pushFollow(FOLLOW_21);
            rule__RouteRule__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RouteRule__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__Group__2"


    // $ANTLR start "rule__RouteRule__Group__2__Impl"
    // InternalShardML.g:1795:1: rule__RouteRule__Group__2__Impl : ( '{' ) ;
    public final void rule__RouteRule__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1799:1: ( ( '{' ) )
            // InternalShardML.g:1800:1: ( '{' )
            {
            // InternalShardML.g:1800:1: ( '{' )
            // InternalShardML.g:1801:2: '{'
            {
             before(grammarAccess.getRouteRuleAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,34,FOLLOW_2); 
             after(grammarAccess.getRouteRuleAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__Group__2__Impl"


    // $ANTLR start "rule__RouteRule__Group__3"
    // InternalShardML.g:1810:1: rule__RouteRule__Group__3 : rule__RouteRule__Group__3__Impl rule__RouteRule__Group__4 ;
    public final void rule__RouteRule__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1814:1: ( rule__RouteRule__Group__3__Impl rule__RouteRule__Group__4 )
            // InternalShardML.g:1815:2: rule__RouteRule__Group__3__Impl rule__RouteRule__Group__4
            {
            pushFollow(FOLLOW_22);
            rule__RouteRule__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RouteRule__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__Group__3"


    // $ANTLR start "rule__RouteRule__Group__3__Impl"
    // InternalShardML.g:1822:1: rule__RouteRule__Group__3__Impl : ( 'policy:' ) ;
    public final void rule__RouteRule__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1826:1: ( ( 'policy:' ) )
            // InternalShardML.g:1827:1: ( 'policy:' )
            {
            // InternalShardML.g:1827:1: ( 'policy:' )
            // InternalShardML.g:1828:2: 'policy:'
            {
             before(grammarAccess.getRouteRuleAccess().getPolicyKeyword_3()); 
            match(input,46,FOLLOW_2); 
             after(grammarAccess.getRouteRuleAccess().getPolicyKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__Group__3__Impl"


    // $ANTLR start "rule__RouteRule__Group__4"
    // InternalShardML.g:1837:1: rule__RouteRule__Group__4 : rule__RouteRule__Group__4__Impl rule__RouteRule__Group__5 ;
    public final void rule__RouteRule__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1841:1: ( rule__RouteRule__Group__4__Impl rule__RouteRule__Group__5 )
            // InternalShardML.g:1842:2: rule__RouteRule__Group__4__Impl rule__RouteRule__Group__5
            {
            pushFollow(FOLLOW_23);
            rule__RouteRule__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RouteRule__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__Group__4"


    // $ANTLR start "rule__RouteRule__Group__4__Impl"
    // InternalShardML.g:1849:1: rule__RouteRule__Group__4__Impl : ( ( rule__RouteRule__PolicyAssignment_4 ) ) ;
    public final void rule__RouteRule__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1853:1: ( ( ( rule__RouteRule__PolicyAssignment_4 ) ) )
            // InternalShardML.g:1854:1: ( ( rule__RouteRule__PolicyAssignment_4 ) )
            {
            // InternalShardML.g:1854:1: ( ( rule__RouteRule__PolicyAssignment_4 ) )
            // InternalShardML.g:1855:2: ( rule__RouteRule__PolicyAssignment_4 )
            {
             before(grammarAccess.getRouteRuleAccess().getPolicyAssignment_4()); 
            // InternalShardML.g:1856:2: ( rule__RouteRule__PolicyAssignment_4 )
            // InternalShardML.g:1856:3: rule__RouteRule__PolicyAssignment_4
            {
            pushFollow(FOLLOW_2);
            rule__RouteRule__PolicyAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getRouteRuleAccess().getPolicyAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__Group__4__Impl"


    // $ANTLR start "rule__RouteRule__Group__5"
    // InternalShardML.g:1864:1: rule__RouteRule__Group__5 : rule__RouteRule__Group__5__Impl rule__RouteRule__Group__6 ;
    public final void rule__RouteRule__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1868:1: ( rule__RouteRule__Group__5__Impl rule__RouteRule__Group__6 )
            // InternalShardML.g:1869:2: rule__RouteRule__Group__5__Impl rule__RouteRule__Group__6
            {
            pushFollow(FOLLOW_23);
            rule__RouteRule__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RouteRule__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__Group__5"


    // $ANTLR start "rule__RouteRule__Group__5__Impl"
    // InternalShardML.g:1876:1: rule__RouteRule__Group__5__Impl : ( ( rule__RouteRule__Group_5__0 )? ) ;
    public final void rule__RouteRule__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1880:1: ( ( ( rule__RouteRule__Group_5__0 )? ) )
            // InternalShardML.g:1881:1: ( ( rule__RouteRule__Group_5__0 )? )
            {
            // InternalShardML.g:1881:1: ( ( rule__RouteRule__Group_5__0 )? )
            // InternalShardML.g:1882:2: ( rule__RouteRule__Group_5__0 )?
            {
             before(grammarAccess.getRouteRuleAccess().getGroup_5()); 
            // InternalShardML.g:1883:2: ( rule__RouteRule__Group_5__0 )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==47) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalShardML.g:1883:3: rule__RouteRule__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__RouteRule__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getRouteRuleAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__Group__5__Impl"


    // $ANTLR start "rule__RouteRule__Group__6"
    // InternalShardML.g:1891:1: rule__RouteRule__Group__6 : rule__RouteRule__Group__6__Impl rule__RouteRule__Group__7 ;
    public final void rule__RouteRule__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1895:1: ( rule__RouteRule__Group__6__Impl rule__RouteRule__Group__7 )
            // InternalShardML.g:1896:2: rule__RouteRule__Group__6__Impl rule__RouteRule__Group__7
            {
            pushFollow(FOLLOW_23);
            rule__RouteRule__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RouteRule__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__Group__6"


    // $ANTLR start "rule__RouteRule__Group__6__Impl"
    // InternalShardML.g:1903:1: rule__RouteRule__Group__6__Impl : ( ( rule__RouteRule__QueriesAssignment_6 )* ) ;
    public final void rule__RouteRule__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1907:1: ( ( ( rule__RouteRule__QueriesAssignment_6 )* ) )
            // InternalShardML.g:1908:1: ( ( rule__RouteRule__QueriesAssignment_6 )* )
            {
            // InternalShardML.g:1908:1: ( ( rule__RouteRule__QueriesAssignment_6 )* )
            // InternalShardML.g:1909:2: ( rule__RouteRule__QueriesAssignment_6 )*
            {
             before(grammarAccess.getRouteRuleAccess().getQueriesAssignment_6()); 
            // InternalShardML.g:1910:2: ( rule__RouteRule__QueriesAssignment_6 )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==48) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalShardML.g:1910:3: rule__RouteRule__QueriesAssignment_6
            	    {
            	    pushFollow(FOLLOW_24);
            	    rule__RouteRule__QueriesAssignment_6();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

             after(grammarAccess.getRouteRuleAccess().getQueriesAssignment_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__Group__6__Impl"


    // $ANTLR start "rule__RouteRule__Group__7"
    // InternalShardML.g:1918:1: rule__RouteRule__Group__7 : rule__RouteRule__Group__7__Impl ;
    public final void rule__RouteRule__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1922:1: ( rule__RouteRule__Group__7__Impl )
            // InternalShardML.g:1923:2: rule__RouteRule__Group__7__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RouteRule__Group__7__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__Group__7"


    // $ANTLR start "rule__RouteRule__Group__7__Impl"
    // InternalShardML.g:1929:1: rule__RouteRule__Group__7__Impl : ( '}' ) ;
    public final void rule__RouteRule__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1933:1: ( ( '}' ) )
            // InternalShardML.g:1934:1: ( '}' )
            {
            // InternalShardML.g:1934:1: ( '}' )
            // InternalShardML.g:1935:2: '}'
            {
             before(grammarAccess.getRouteRuleAccess().getRightCurlyBracketKeyword_7()); 
            match(input,36,FOLLOW_2); 
             after(grammarAccess.getRouteRuleAccess().getRightCurlyBracketKeyword_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__Group__7__Impl"


    // $ANTLR start "rule__RouteRule__Group_5__0"
    // InternalShardML.g:1945:1: rule__RouteRule__Group_5__0 : rule__RouteRule__Group_5__0__Impl rule__RouteRule__Group_5__1 ;
    public final void rule__RouteRule__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1949:1: ( rule__RouteRule__Group_5__0__Impl rule__RouteRule__Group_5__1 )
            // InternalShardML.g:1950:2: rule__RouteRule__Group_5__0__Impl rule__RouteRule__Group_5__1
            {
            pushFollow(FOLLOW_20);
            rule__RouteRule__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__RouteRule__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__Group_5__0"


    // $ANTLR start "rule__RouteRule__Group_5__0__Impl"
    // InternalShardML.g:1957:1: rule__RouteRule__Group_5__0__Impl : ( 'max_conditions:' ) ;
    public final void rule__RouteRule__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1961:1: ( ( 'max_conditions:' ) )
            // InternalShardML.g:1962:1: ( 'max_conditions:' )
            {
            // InternalShardML.g:1962:1: ( 'max_conditions:' )
            // InternalShardML.g:1963:2: 'max_conditions:'
            {
             before(grammarAccess.getRouteRuleAccess().getMax_conditionsKeyword_5_0()); 
            match(input,47,FOLLOW_2); 
             after(grammarAccess.getRouteRuleAccess().getMax_conditionsKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__Group_5__0__Impl"


    // $ANTLR start "rule__RouteRule__Group_5__1"
    // InternalShardML.g:1972:1: rule__RouteRule__Group_5__1 : rule__RouteRule__Group_5__1__Impl ;
    public final void rule__RouteRule__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1976:1: ( rule__RouteRule__Group_5__1__Impl )
            // InternalShardML.g:1977:2: rule__RouteRule__Group_5__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__RouteRule__Group_5__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__Group_5__1"


    // $ANTLR start "rule__RouteRule__Group_5__1__Impl"
    // InternalShardML.g:1983:1: rule__RouteRule__Group_5__1__Impl : ( ( rule__RouteRule__CondtionsAssignment_5_1 ) ) ;
    public final void rule__RouteRule__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:1987:1: ( ( ( rule__RouteRule__CondtionsAssignment_5_1 ) ) )
            // InternalShardML.g:1988:1: ( ( rule__RouteRule__CondtionsAssignment_5_1 ) )
            {
            // InternalShardML.g:1988:1: ( ( rule__RouteRule__CondtionsAssignment_5_1 ) )
            // InternalShardML.g:1989:2: ( rule__RouteRule__CondtionsAssignment_5_1 )
            {
             before(grammarAccess.getRouteRuleAccess().getCondtionsAssignment_5_1()); 
            // InternalShardML.g:1990:2: ( rule__RouteRule__CondtionsAssignment_5_1 )
            // InternalShardML.g:1990:3: rule__RouteRule__CondtionsAssignment_5_1
            {
            pushFollow(FOLLOW_2);
            rule__RouteRule__CondtionsAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getRouteRuleAccess().getCondtionsAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__Group_5__1__Impl"


    // $ANTLR start "rule__QueryPattern__Group__0"
    // InternalShardML.g:1999:1: rule__QueryPattern__Group__0 : rule__QueryPattern__Group__0__Impl rule__QueryPattern__Group__1 ;
    public final void rule__QueryPattern__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2003:1: ( rule__QueryPattern__Group__0__Impl rule__QueryPattern__Group__1 )
            // InternalShardML.g:2004:2: rule__QueryPattern__Group__0__Impl rule__QueryPattern__Group__1
            {
            pushFollow(FOLLOW_6);
            rule__QueryPattern__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__QueryPattern__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group__0"


    // $ANTLR start "rule__QueryPattern__Group__0__Impl"
    // InternalShardML.g:2011:1: rule__QueryPattern__Group__0__Impl : ( 'query' ) ;
    public final void rule__QueryPattern__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2015:1: ( ( 'query' ) )
            // InternalShardML.g:2016:1: ( 'query' )
            {
            // InternalShardML.g:2016:1: ( 'query' )
            // InternalShardML.g:2017:2: 'query'
            {
             before(grammarAccess.getQueryPatternAccess().getQueryKeyword_0()); 
            match(input,48,FOLLOW_2); 
             after(grammarAccess.getQueryPatternAccess().getQueryKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group__0__Impl"


    // $ANTLR start "rule__QueryPattern__Group__1"
    // InternalShardML.g:2026:1: rule__QueryPattern__Group__1 : rule__QueryPattern__Group__1__Impl rule__QueryPattern__Group__2 ;
    public final void rule__QueryPattern__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2030:1: ( rule__QueryPattern__Group__1__Impl rule__QueryPattern__Group__2 )
            // InternalShardML.g:2031:2: rule__QueryPattern__Group__1__Impl rule__QueryPattern__Group__2
            {
            pushFollow(FOLLOW_7);
            rule__QueryPattern__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__QueryPattern__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group__1"


    // $ANTLR start "rule__QueryPattern__Group__1__Impl"
    // InternalShardML.g:2038:1: rule__QueryPattern__Group__1__Impl : ( ( rule__QueryPattern__NameAssignment_1 ) ) ;
    public final void rule__QueryPattern__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2042:1: ( ( ( rule__QueryPattern__NameAssignment_1 ) ) )
            // InternalShardML.g:2043:1: ( ( rule__QueryPattern__NameAssignment_1 ) )
            {
            // InternalShardML.g:2043:1: ( ( rule__QueryPattern__NameAssignment_1 ) )
            // InternalShardML.g:2044:2: ( rule__QueryPattern__NameAssignment_1 )
            {
             before(grammarAccess.getQueryPatternAccess().getNameAssignment_1()); 
            // InternalShardML.g:2045:2: ( rule__QueryPattern__NameAssignment_1 )
            // InternalShardML.g:2045:3: rule__QueryPattern__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__QueryPattern__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getQueryPatternAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group__1__Impl"


    // $ANTLR start "rule__QueryPattern__Group__2"
    // InternalShardML.g:2053:1: rule__QueryPattern__Group__2 : rule__QueryPattern__Group__2__Impl rule__QueryPattern__Group__3 ;
    public final void rule__QueryPattern__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2057:1: ( rule__QueryPattern__Group__2__Impl rule__QueryPattern__Group__3 )
            // InternalShardML.g:2058:2: rule__QueryPattern__Group__2__Impl rule__QueryPattern__Group__3
            {
            pushFollow(FOLLOW_8);
            rule__QueryPattern__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__QueryPattern__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group__2"


    // $ANTLR start "rule__QueryPattern__Group__2__Impl"
    // InternalShardML.g:2065:1: rule__QueryPattern__Group__2__Impl : ( '{' ) ;
    public final void rule__QueryPattern__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2069:1: ( ( '{' ) )
            // InternalShardML.g:2070:1: ( '{' )
            {
            // InternalShardML.g:2070:1: ( '{' )
            // InternalShardML.g:2071:2: '{'
            {
             before(grammarAccess.getQueryPatternAccess().getLeftCurlyBracketKeyword_2()); 
            match(input,34,FOLLOW_2); 
             after(grammarAccess.getQueryPatternAccess().getLeftCurlyBracketKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group__2__Impl"


    // $ANTLR start "rule__QueryPattern__Group__3"
    // InternalShardML.g:2080:1: rule__QueryPattern__Group__3 : rule__QueryPattern__Group__3__Impl rule__QueryPattern__Group__4 ;
    public final void rule__QueryPattern__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2084:1: ( rule__QueryPattern__Group__3__Impl rule__QueryPattern__Group__4 )
            // InternalShardML.g:2085:2: rule__QueryPattern__Group__3__Impl rule__QueryPattern__Group__4
            {
            pushFollow(FOLLOW_25);
            rule__QueryPattern__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__QueryPattern__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group__3"


    // $ANTLR start "rule__QueryPattern__Group__3__Impl"
    // InternalShardML.g:2092:1: rule__QueryPattern__Group__3__Impl : ( 'type:' ) ;
    public final void rule__QueryPattern__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2096:1: ( ( 'type:' ) )
            // InternalShardML.g:2097:1: ( 'type:' )
            {
            // InternalShardML.g:2097:1: ( 'type:' )
            // InternalShardML.g:2098:2: 'type:'
            {
             before(grammarAccess.getQueryPatternAccess().getTypeKeyword_3()); 
            match(input,35,FOLLOW_2); 
             after(grammarAccess.getQueryPatternAccess().getTypeKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group__3__Impl"


    // $ANTLR start "rule__QueryPattern__Group__4"
    // InternalShardML.g:2107:1: rule__QueryPattern__Group__4 : rule__QueryPattern__Group__4__Impl rule__QueryPattern__Group__5 ;
    public final void rule__QueryPattern__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2111:1: ( rule__QueryPattern__Group__4__Impl rule__QueryPattern__Group__5 )
            // InternalShardML.g:2112:2: rule__QueryPattern__Group__4__Impl rule__QueryPattern__Group__5
            {
            pushFollow(FOLLOW_26);
            rule__QueryPattern__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__QueryPattern__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group__4"


    // $ANTLR start "rule__QueryPattern__Group__4__Impl"
    // InternalShardML.g:2119:1: rule__QueryPattern__Group__4__Impl : ( ( rule__QueryPattern__QueryTypeAssignment_4 ) ) ;
    public final void rule__QueryPattern__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2123:1: ( ( ( rule__QueryPattern__QueryTypeAssignment_4 ) ) )
            // InternalShardML.g:2124:1: ( ( rule__QueryPattern__QueryTypeAssignment_4 ) )
            {
            // InternalShardML.g:2124:1: ( ( rule__QueryPattern__QueryTypeAssignment_4 ) )
            // InternalShardML.g:2125:2: ( rule__QueryPattern__QueryTypeAssignment_4 )
            {
             before(grammarAccess.getQueryPatternAccess().getQueryTypeAssignment_4()); 
            // InternalShardML.g:2126:2: ( rule__QueryPattern__QueryTypeAssignment_4 )
            // InternalShardML.g:2126:3: rule__QueryPattern__QueryTypeAssignment_4
            {
            pushFollow(FOLLOW_2);
            rule__QueryPattern__QueryTypeAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getQueryPatternAccess().getQueryTypeAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group__4__Impl"


    // $ANTLR start "rule__QueryPattern__Group__5"
    // InternalShardML.g:2134:1: rule__QueryPattern__Group__5 : rule__QueryPattern__Group__5__Impl rule__QueryPattern__Group__6 ;
    public final void rule__QueryPattern__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2138:1: ( rule__QueryPattern__Group__5__Impl rule__QueryPattern__Group__6 )
            // InternalShardML.g:2139:2: rule__QueryPattern__Group__5__Impl rule__QueryPattern__Group__6
            {
            pushFollow(FOLLOW_26);
            rule__QueryPattern__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__QueryPattern__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group__5"


    // $ANTLR start "rule__QueryPattern__Group__5__Impl"
    // InternalShardML.g:2146:1: rule__QueryPattern__Group__5__Impl : ( ( rule__QueryPattern__Group_5__0 )? ) ;
    public final void rule__QueryPattern__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2150:1: ( ( ( rule__QueryPattern__Group_5__0 )? ) )
            // InternalShardML.g:2151:1: ( ( rule__QueryPattern__Group_5__0 )? )
            {
            // InternalShardML.g:2151:1: ( ( rule__QueryPattern__Group_5__0 )? )
            // InternalShardML.g:2152:2: ( rule__QueryPattern__Group_5__0 )?
            {
             before(grammarAccess.getQueryPatternAccess().getGroup_5()); 
            // InternalShardML.g:2153:2: ( rule__QueryPattern__Group_5__0 )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==49) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalShardML.g:2153:3: rule__QueryPattern__Group_5__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__QueryPattern__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getQueryPatternAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group__5__Impl"


    // $ANTLR start "rule__QueryPattern__Group__6"
    // InternalShardML.g:2161:1: rule__QueryPattern__Group__6 : rule__QueryPattern__Group__6__Impl rule__QueryPattern__Group__7 ;
    public final void rule__QueryPattern__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2165:1: ( rule__QueryPattern__Group__6__Impl rule__QueryPattern__Group__7 )
            // InternalShardML.g:2166:2: rule__QueryPattern__Group__6__Impl rule__QueryPattern__Group__7
            {
            pushFollow(FOLLOW_26);
            rule__QueryPattern__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__QueryPattern__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group__6"


    // $ANTLR start "rule__QueryPattern__Group__6__Impl"
    // InternalShardML.g:2173:1: rule__QueryPattern__Group__6__Impl : ( ( rule__QueryPattern__Group_6__0 )? ) ;
    public final void rule__QueryPattern__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2177:1: ( ( ( rule__QueryPattern__Group_6__0 )? ) )
            // InternalShardML.g:2178:1: ( ( rule__QueryPattern__Group_6__0 )? )
            {
            // InternalShardML.g:2178:1: ( ( rule__QueryPattern__Group_6__0 )? )
            // InternalShardML.g:2179:2: ( rule__QueryPattern__Group_6__0 )?
            {
             before(grammarAccess.getQueryPatternAccess().getGroup_6()); 
            // InternalShardML.g:2180:2: ( rule__QueryPattern__Group_6__0 )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==50) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalShardML.g:2180:3: rule__QueryPattern__Group_6__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__QueryPattern__Group_6__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getQueryPatternAccess().getGroup_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group__6__Impl"


    // $ANTLR start "rule__QueryPattern__Group__7"
    // InternalShardML.g:2188:1: rule__QueryPattern__Group__7 : rule__QueryPattern__Group__7__Impl rule__QueryPattern__Group__8 ;
    public final void rule__QueryPattern__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2192:1: ( rule__QueryPattern__Group__7__Impl rule__QueryPattern__Group__8 )
            // InternalShardML.g:2193:2: rule__QueryPattern__Group__7__Impl rule__QueryPattern__Group__8
            {
            pushFollow(FOLLOW_26);
            rule__QueryPattern__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__QueryPattern__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group__7"


    // $ANTLR start "rule__QueryPattern__Group__7__Impl"
    // InternalShardML.g:2200:1: rule__QueryPattern__Group__7__Impl : ( ( rule__QueryPattern__Group_7__0 )? ) ;
    public final void rule__QueryPattern__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2204:1: ( ( ( rule__QueryPattern__Group_7__0 )? ) )
            // InternalShardML.g:2205:1: ( ( rule__QueryPattern__Group_7__0 )? )
            {
            // InternalShardML.g:2205:1: ( ( rule__QueryPattern__Group_7__0 )? )
            // InternalShardML.g:2206:2: ( rule__QueryPattern__Group_7__0 )?
            {
             before(grammarAccess.getQueryPatternAccess().getGroup_7()); 
            // InternalShardML.g:2207:2: ( rule__QueryPattern__Group_7__0 )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==51) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalShardML.g:2207:3: rule__QueryPattern__Group_7__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__QueryPattern__Group_7__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getQueryPatternAccess().getGroup_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group__7__Impl"


    // $ANTLR start "rule__QueryPattern__Group__8"
    // InternalShardML.g:2215:1: rule__QueryPattern__Group__8 : rule__QueryPattern__Group__8__Impl rule__QueryPattern__Group__9 ;
    public final void rule__QueryPattern__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2219:1: ( rule__QueryPattern__Group__8__Impl rule__QueryPattern__Group__9 )
            // InternalShardML.g:2220:2: rule__QueryPattern__Group__8__Impl rule__QueryPattern__Group__9
            {
            pushFollow(FOLLOW_26);
            rule__QueryPattern__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__QueryPattern__Group__9();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group__8"


    // $ANTLR start "rule__QueryPattern__Group__8__Impl"
    // InternalShardML.g:2227:1: rule__QueryPattern__Group__8__Impl : ( ( rule__QueryPattern__Group_8__0 )? ) ;
    public final void rule__QueryPattern__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2231:1: ( ( ( rule__QueryPattern__Group_8__0 )? ) )
            // InternalShardML.g:2232:1: ( ( rule__QueryPattern__Group_8__0 )? )
            {
            // InternalShardML.g:2232:1: ( ( rule__QueryPattern__Group_8__0 )? )
            // InternalShardML.g:2233:2: ( rule__QueryPattern__Group_8__0 )?
            {
             before(grammarAccess.getQueryPatternAccess().getGroup_8()); 
            // InternalShardML.g:2234:2: ( rule__QueryPattern__Group_8__0 )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==52) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalShardML.g:2234:3: rule__QueryPattern__Group_8__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__QueryPattern__Group_8__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getQueryPatternAccess().getGroup_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group__8__Impl"


    // $ANTLR start "rule__QueryPattern__Group__9"
    // InternalShardML.g:2242:1: rule__QueryPattern__Group__9 : rule__QueryPattern__Group__9__Impl ;
    public final void rule__QueryPattern__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2246:1: ( rule__QueryPattern__Group__9__Impl )
            // InternalShardML.g:2247:2: rule__QueryPattern__Group__9__Impl
            {
            pushFollow(FOLLOW_2);
            rule__QueryPattern__Group__9__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group__9"


    // $ANTLR start "rule__QueryPattern__Group__9__Impl"
    // InternalShardML.g:2253:1: rule__QueryPattern__Group__9__Impl : ( '}' ) ;
    public final void rule__QueryPattern__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2257:1: ( ( '}' ) )
            // InternalShardML.g:2258:1: ( '}' )
            {
            // InternalShardML.g:2258:1: ( '}' )
            // InternalShardML.g:2259:2: '}'
            {
             before(grammarAccess.getQueryPatternAccess().getRightCurlyBracketKeyword_9()); 
            match(input,36,FOLLOW_2); 
             after(grammarAccess.getQueryPatternAccess().getRightCurlyBracketKeyword_9()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group__9__Impl"


    // $ANTLR start "rule__QueryPattern__Group_5__0"
    // InternalShardML.g:2269:1: rule__QueryPattern__Group_5__0 : rule__QueryPattern__Group_5__0__Impl rule__QueryPattern__Group_5__1 ;
    public final void rule__QueryPattern__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2273:1: ( rule__QueryPattern__Group_5__0__Impl rule__QueryPattern__Group_5__1 )
            // InternalShardML.g:2274:2: rule__QueryPattern__Group_5__0__Impl rule__QueryPattern__Group_5__1
            {
            pushFollow(FOLLOW_6);
            rule__QueryPattern__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__QueryPattern__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group_5__0"


    // $ANTLR start "rule__QueryPattern__Group_5__0__Impl"
    // InternalShardML.g:2281:1: rule__QueryPattern__Group_5__0__Impl : ( 'where:' ) ;
    public final void rule__QueryPattern__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2285:1: ( ( 'where:' ) )
            // InternalShardML.g:2286:1: ( 'where:' )
            {
            // InternalShardML.g:2286:1: ( 'where:' )
            // InternalShardML.g:2287:2: 'where:'
            {
             before(grammarAccess.getQueryPatternAccess().getWhereKeyword_5_0()); 
            match(input,49,FOLLOW_2); 
             after(grammarAccess.getQueryPatternAccess().getWhereKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group_5__0__Impl"


    // $ANTLR start "rule__QueryPattern__Group_5__1"
    // InternalShardML.g:2296:1: rule__QueryPattern__Group_5__1 : rule__QueryPattern__Group_5__1__Impl ;
    public final void rule__QueryPattern__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2300:1: ( rule__QueryPattern__Group_5__1__Impl )
            // InternalShardML.g:2301:2: rule__QueryPattern__Group_5__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__QueryPattern__Group_5__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group_5__1"


    // $ANTLR start "rule__QueryPattern__Group_5__1__Impl"
    // InternalShardML.g:2307:1: rule__QueryPattern__Group_5__1__Impl : ( ( rule__QueryPattern__WhereAssignment_5_1 ) ) ;
    public final void rule__QueryPattern__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2311:1: ( ( ( rule__QueryPattern__WhereAssignment_5_1 ) ) )
            // InternalShardML.g:2312:1: ( ( rule__QueryPattern__WhereAssignment_5_1 ) )
            {
            // InternalShardML.g:2312:1: ( ( rule__QueryPattern__WhereAssignment_5_1 ) )
            // InternalShardML.g:2313:2: ( rule__QueryPattern__WhereAssignment_5_1 )
            {
             before(grammarAccess.getQueryPatternAccess().getWhereAssignment_5_1()); 
            // InternalShardML.g:2314:2: ( rule__QueryPattern__WhereAssignment_5_1 )
            // InternalShardML.g:2314:3: rule__QueryPattern__WhereAssignment_5_1
            {
            pushFollow(FOLLOW_2);
            rule__QueryPattern__WhereAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getQueryPatternAccess().getWhereAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group_5__1__Impl"


    // $ANTLR start "rule__QueryPattern__Group_6__0"
    // InternalShardML.g:2323:1: rule__QueryPattern__Group_6__0 : rule__QueryPattern__Group_6__0__Impl rule__QueryPattern__Group_6__1 ;
    public final void rule__QueryPattern__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2327:1: ( rule__QueryPattern__Group_6__0__Impl rule__QueryPattern__Group_6__1 )
            // InternalShardML.g:2328:2: rule__QueryPattern__Group_6__0__Impl rule__QueryPattern__Group_6__1
            {
            pushFollow(FOLLOW_6);
            rule__QueryPattern__Group_6__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__QueryPattern__Group_6__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group_6__0"


    // $ANTLR start "rule__QueryPattern__Group_6__0__Impl"
    // InternalShardML.g:2335:1: rule__QueryPattern__Group_6__0__Impl : ( 'sort:' ) ;
    public final void rule__QueryPattern__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2339:1: ( ( 'sort:' ) )
            // InternalShardML.g:2340:1: ( 'sort:' )
            {
            // InternalShardML.g:2340:1: ( 'sort:' )
            // InternalShardML.g:2341:2: 'sort:'
            {
             before(grammarAccess.getQueryPatternAccess().getSortKeyword_6_0()); 
            match(input,50,FOLLOW_2); 
             after(grammarAccess.getQueryPatternAccess().getSortKeyword_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group_6__0__Impl"


    // $ANTLR start "rule__QueryPattern__Group_6__1"
    // InternalShardML.g:2350:1: rule__QueryPattern__Group_6__1 : rule__QueryPattern__Group_6__1__Impl ;
    public final void rule__QueryPattern__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2354:1: ( rule__QueryPattern__Group_6__1__Impl )
            // InternalShardML.g:2355:2: rule__QueryPattern__Group_6__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__QueryPattern__Group_6__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group_6__1"


    // $ANTLR start "rule__QueryPattern__Group_6__1__Impl"
    // InternalShardML.g:2361:1: rule__QueryPattern__Group_6__1__Impl : ( ( rule__QueryPattern__SortAssignment_6_1 ) ) ;
    public final void rule__QueryPattern__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2365:1: ( ( ( rule__QueryPattern__SortAssignment_6_1 ) ) )
            // InternalShardML.g:2366:1: ( ( rule__QueryPattern__SortAssignment_6_1 ) )
            {
            // InternalShardML.g:2366:1: ( ( rule__QueryPattern__SortAssignment_6_1 ) )
            // InternalShardML.g:2367:2: ( rule__QueryPattern__SortAssignment_6_1 )
            {
             before(grammarAccess.getQueryPatternAccess().getSortAssignment_6_1()); 
            // InternalShardML.g:2368:2: ( rule__QueryPattern__SortAssignment_6_1 )
            // InternalShardML.g:2368:3: rule__QueryPattern__SortAssignment_6_1
            {
            pushFollow(FOLLOW_2);
            rule__QueryPattern__SortAssignment_6_1();

            state._fsp--;


            }

             after(grammarAccess.getQueryPatternAccess().getSortAssignment_6_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group_6__1__Impl"


    // $ANTLR start "rule__QueryPattern__Group_7__0"
    // InternalShardML.g:2377:1: rule__QueryPattern__Group_7__0 : rule__QueryPattern__Group_7__0__Impl rule__QueryPattern__Group_7__1 ;
    public final void rule__QueryPattern__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2381:1: ( rule__QueryPattern__Group_7__0__Impl rule__QueryPattern__Group_7__1 )
            // InternalShardML.g:2382:2: rule__QueryPattern__Group_7__0__Impl rule__QueryPattern__Group_7__1
            {
            pushFollow(FOLLOW_6);
            rule__QueryPattern__Group_7__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__QueryPattern__Group_7__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group_7__0"


    // $ANTLR start "rule__QueryPattern__Group_7__0__Impl"
    // InternalShardML.g:2389:1: rule__QueryPattern__Group_7__0__Impl : ( 'group_by:' ) ;
    public final void rule__QueryPattern__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2393:1: ( ( 'group_by:' ) )
            // InternalShardML.g:2394:1: ( 'group_by:' )
            {
            // InternalShardML.g:2394:1: ( 'group_by:' )
            // InternalShardML.g:2395:2: 'group_by:'
            {
             before(grammarAccess.getQueryPatternAccess().getGroup_byKeyword_7_0()); 
            match(input,51,FOLLOW_2); 
             after(grammarAccess.getQueryPatternAccess().getGroup_byKeyword_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group_7__0__Impl"


    // $ANTLR start "rule__QueryPattern__Group_7__1"
    // InternalShardML.g:2404:1: rule__QueryPattern__Group_7__1 : rule__QueryPattern__Group_7__1__Impl ;
    public final void rule__QueryPattern__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2408:1: ( rule__QueryPattern__Group_7__1__Impl )
            // InternalShardML.g:2409:2: rule__QueryPattern__Group_7__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__QueryPattern__Group_7__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group_7__1"


    // $ANTLR start "rule__QueryPattern__Group_7__1__Impl"
    // InternalShardML.g:2415:1: rule__QueryPattern__Group_7__1__Impl : ( ( rule__QueryPattern__GroupAssignment_7_1 ) ) ;
    public final void rule__QueryPattern__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2419:1: ( ( ( rule__QueryPattern__GroupAssignment_7_1 ) ) )
            // InternalShardML.g:2420:1: ( ( rule__QueryPattern__GroupAssignment_7_1 ) )
            {
            // InternalShardML.g:2420:1: ( ( rule__QueryPattern__GroupAssignment_7_1 ) )
            // InternalShardML.g:2421:2: ( rule__QueryPattern__GroupAssignment_7_1 )
            {
             before(grammarAccess.getQueryPatternAccess().getGroupAssignment_7_1()); 
            // InternalShardML.g:2422:2: ( rule__QueryPattern__GroupAssignment_7_1 )
            // InternalShardML.g:2422:3: rule__QueryPattern__GroupAssignment_7_1
            {
            pushFollow(FOLLOW_2);
            rule__QueryPattern__GroupAssignment_7_1();

            state._fsp--;


            }

             after(grammarAccess.getQueryPatternAccess().getGroupAssignment_7_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group_7__1__Impl"


    // $ANTLR start "rule__QueryPattern__Group_8__0"
    // InternalShardML.g:2431:1: rule__QueryPattern__Group_8__0 : rule__QueryPattern__Group_8__0__Impl rule__QueryPattern__Group_8__1 ;
    public final void rule__QueryPattern__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2435:1: ( rule__QueryPattern__Group_8__0__Impl rule__QueryPattern__Group_8__1 )
            // InternalShardML.g:2436:2: rule__QueryPattern__Group_8__0__Impl rule__QueryPattern__Group_8__1
            {
            pushFollow(FOLLOW_27);
            rule__QueryPattern__Group_8__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__QueryPattern__Group_8__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group_8__0"


    // $ANTLR start "rule__QueryPattern__Group_8__0__Impl"
    // InternalShardML.g:2443:1: rule__QueryPattern__Group_8__0__Impl : ( 'distinct:' ) ;
    public final void rule__QueryPattern__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2447:1: ( ( 'distinct:' ) )
            // InternalShardML.g:2448:1: ( 'distinct:' )
            {
            // InternalShardML.g:2448:1: ( 'distinct:' )
            // InternalShardML.g:2449:2: 'distinct:'
            {
             before(grammarAccess.getQueryPatternAccess().getDistinctKeyword_8_0()); 
            match(input,52,FOLLOW_2); 
             after(grammarAccess.getQueryPatternAccess().getDistinctKeyword_8_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group_8__0__Impl"


    // $ANTLR start "rule__QueryPattern__Group_8__1"
    // InternalShardML.g:2458:1: rule__QueryPattern__Group_8__1 : rule__QueryPattern__Group_8__1__Impl ;
    public final void rule__QueryPattern__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2462:1: ( rule__QueryPattern__Group_8__1__Impl )
            // InternalShardML.g:2463:2: rule__QueryPattern__Group_8__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__QueryPattern__Group_8__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group_8__1"


    // $ANTLR start "rule__QueryPattern__Group_8__1__Impl"
    // InternalShardML.g:2469:1: rule__QueryPattern__Group_8__1__Impl : ( ( rule__QueryPattern__DistinctAssignment_8_1 ) ) ;
    public final void rule__QueryPattern__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2473:1: ( ( ( rule__QueryPattern__DistinctAssignment_8_1 ) ) )
            // InternalShardML.g:2474:1: ( ( rule__QueryPattern__DistinctAssignment_8_1 ) )
            {
            // InternalShardML.g:2474:1: ( ( rule__QueryPattern__DistinctAssignment_8_1 ) )
            // InternalShardML.g:2475:2: ( rule__QueryPattern__DistinctAssignment_8_1 )
            {
             before(grammarAccess.getQueryPatternAccess().getDistinctAssignment_8_1()); 
            // InternalShardML.g:2476:2: ( rule__QueryPattern__DistinctAssignment_8_1 )
            // InternalShardML.g:2476:3: rule__QueryPattern__DistinctAssignment_8_1
            {
            pushFollow(FOLLOW_2);
            rule__QueryPattern__DistinctAssignment_8_1();

            state._fsp--;


            }

             after(grammarAccess.getQueryPatternAccess().getDistinctAssignment_8_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__Group_8__1__Impl"


    // $ANTLR start "rule__WhereClause__Group__0"
    // InternalShardML.g:2485:1: rule__WhereClause__Group__0 : rule__WhereClause__Group__0__Impl rule__WhereClause__Group__1 ;
    public final void rule__WhereClause__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2489:1: ( rule__WhereClause__Group__0__Impl rule__WhereClause__Group__1 )
            // InternalShardML.g:2490:2: rule__WhereClause__Group__0__Impl rule__WhereClause__Group__1
            {
            pushFollow(FOLLOW_28);
            rule__WhereClause__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__WhereClause__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WhereClause__Group__0"


    // $ANTLR start "rule__WhereClause__Group__0__Impl"
    // InternalShardML.g:2497:1: rule__WhereClause__Group__0__Impl : ( ( rule__WhereClause__FirstAssignment_0 ) ) ;
    public final void rule__WhereClause__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2501:1: ( ( ( rule__WhereClause__FirstAssignment_0 ) ) )
            // InternalShardML.g:2502:1: ( ( rule__WhereClause__FirstAssignment_0 ) )
            {
            // InternalShardML.g:2502:1: ( ( rule__WhereClause__FirstAssignment_0 ) )
            // InternalShardML.g:2503:2: ( rule__WhereClause__FirstAssignment_0 )
            {
             before(grammarAccess.getWhereClauseAccess().getFirstAssignment_0()); 
            // InternalShardML.g:2504:2: ( rule__WhereClause__FirstAssignment_0 )
            // InternalShardML.g:2504:3: rule__WhereClause__FirstAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__WhereClause__FirstAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getWhereClauseAccess().getFirstAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WhereClause__Group__0__Impl"


    // $ANTLR start "rule__WhereClause__Group__1"
    // InternalShardML.g:2512:1: rule__WhereClause__Group__1 : rule__WhereClause__Group__1__Impl ;
    public final void rule__WhereClause__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2516:1: ( rule__WhereClause__Group__1__Impl )
            // InternalShardML.g:2517:2: rule__WhereClause__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__WhereClause__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WhereClause__Group__1"


    // $ANTLR start "rule__WhereClause__Group__1__Impl"
    // InternalShardML.g:2523:1: rule__WhereClause__Group__1__Impl : ( ( rule__WhereClause__Group_1__0 )* ) ;
    public final void rule__WhereClause__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2527:1: ( ( ( rule__WhereClause__Group_1__0 )* ) )
            // InternalShardML.g:2528:1: ( ( rule__WhereClause__Group_1__0 )* )
            {
            // InternalShardML.g:2528:1: ( ( rule__WhereClause__Group_1__0 )* )
            // InternalShardML.g:2529:2: ( rule__WhereClause__Group_1__0 )*
            {
             before(grammarAccess.getWhereClauseAccess().getGroup_1()); 
            // InternalShardML.g:2530:2: ( rule__WhereClause__Group_1__0 )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( ((LA22_0>=28 && LA22_0<=29)) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalShardML.g:2530:3: rule__WhereClause__Group_1__0
            	    {
            	    pushFollow(FOLLOW_29);
            	    rule__WhereClause__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);

             after(grammarAccess.getWhereClauseAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WhereClause__Group__1__Impl"


    // $ANTLR start "rule__WhereClause__Group_1__0"
    // InternalShardML.g:2539:1: rule__WhereClause__Group_1__0 : rule__WhereClause__Group_1__0__Impl rule__WhereClause__Group_1__1 ;
    public final void rule__WhereClause__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2543:1: ( rule__WhereClause__Group_1__0__Impl rule__WhereClause__Group_1__1 )
            // InternalShardML.g:2544:2: rule__WhereClause__Group_1__0__Impl rule__WhereClause__Group_1__1
            {
            pushFollow(FOLLOW_6);
            rule__WhereClause__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__WhereClause__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WhereClause__Group_1__0"


    // $ANTLR start "rule__WhereClause__Group_1__0__Impl"
    // InternalShardML.g:2551:1: rule__WhereClause__Group_1__0__Impl : ( ( rule__WhereClause__LinkAssignment_1_0 ) ) ;
    public final void rule__WhereClause__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2555:1: ( ( ( rule__WhereClause__LinkAssignment_1_0 ) ) )
            // InternalShardML.g:2556:1: ( ( rule__WhereClause__LinkAssignment_1_0 ) )
            {
            // InternalShardML.g:2556:1: ( ( rule__WhereClause__LinkAssignment_1_0 ) )
            // InternalShardML.g:2557:2: ( rule__WhereClause__LinkAssignment_1_0 )
            {
             before(grammarAccess.getWhereClauseAccess().getLinkAssignment_1_0()); 
            // InternalShardML.g:2558:2: ( rule__WhereClause__LinkAssignment_1_0 )
            // InternalShardML.g:2558:3: rule__WhereClause__LinkAssignment_1_0
            {
            pushFollow(FOLLOW_2);
            rule__WhereClause__LinkAssignment_1_0();

            state._fsp--;


            }

             after(grammarAccess.getWhereClauseAccess().getLinkAssignment_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WhereClause__Group_1__0__Impl"


    // $ANTLR start "rule__WhereClause__Group_1__1"
    // InternalShardML.g:2566:1: rule__WhereClause__Group_1__1 : rule__WhereClause__Group_1__1__Impl ;
    public final void rule__WhereClause__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2570:1: ( rule__WhereClause__Group_1__1__Impl )
            // InternalShardML.g:2571:2: rule__WhereClause__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__WhereClause__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WhereClause__Group_1__1"


    // $ANTLR start "rule__WhereClause__Group_1__1__Impl"
    // InternalShardML.g:2577:1: rule__WhereClause__Group_1__1__Impl : ( ( rule__WhereClause__RestAssignment_1_1 ) ) ;
    public final void rule__WhereClause__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2581:1: ( ( ( rule__WhereClause__RestAssignment_1_1 ) ) )
            // InternalShardML.g:2582:1: ( ( rule__WhereClause__RestAssignment_1_1 ) )
            {
            // InternalShardML.g:2582:1: ( ( rule__WhereClause__RestAssignment_1_1 ) )
            // InternalShardML.g:2583:2: ( rule__WhereClause__RestAssignment_1_1 )
            {
             before(grammarAccess.getWhereClauseAccess().getRestAssignment_1_1()); 
            // InternalShardML.g:2584:2: ( rule__WhereClause__RestAssignment_1_1 )
            // InternalShardML.g:2584:3: rule__WhereClause__RestAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__WhereClause__RestAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getWhereClauseAccess().getRestAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WhereClause__Group_1__1__Impl"


    // $ANTLR start "rule__WhereCondition__Group__0"
    // InternalShardML.g:2593:1: rule__WhereCondition__Group__0 : rule__WhereCondition__Group__0__Impl rule__WhereCondition__Group__1 ;
    public final void rule__WhereCondition__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2597:1: ( rule__WhereCondition__Group__0__Impl rule__WhereCondition__Group__1 )
            // InternalShardML.g:2598:2: rule__WhereCondition__Group__0__Impl rule__WhereCondition__Group__1
            {
            pushFollow(FOLLOW_30);
            rule__WhereCondition__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__WhereCondition__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WhereCondition__Group__0"


    // $ANTLR start "rule__WhereCondition__Group__0__Impl"
    // InternalShardML.g:2605:1: rule__WhereCondition__Group__0__Impl : ( ( rule__WhereCondition__FieldAssignment_0 ) ) ;
    public final void rule__WhereCondition__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2609:1: ( ( ( rule__WhereCondition__FieldAssignment_0 ) ) )
            // InternalShardML.g:2610:1: ( ( rule__WhereCondition__FieldAssignment_0 ) )
            {
            // InternalShardML.g:2610:1: ( ( rule__WhereCondition__FieldAssignment_0 ) )
            // InternalShardML.g:2611:2: ( rule__WhereCondition__FieldAssignment_0 )
            {
             before(grammarAccess.getWhereConditionAccess().getFieldAssignment_0()); 
            // InternalShardML.g:2612:2: ( rule__WhereCondition__FieldAssignment_0 )
            // InternalShardML.g:2612:3: rule__WhereCondition__FieldAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__WhereCondition__FieldAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getWhereConditionAccess().getFieldAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WhereCondition__Group__0__Impl"


    // $ANTLR start "rule__WhereCondition__Group__1"
    // InternalShardML.g:2620:1: rule__WhereCondition__Group__1 : rule__WhereCondition__Group__1__Impl rule__WhereCondition__Group__2 ;
    public final void rule__WhereCondition__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2624:1: ( rule__WhereCondition__Group__1__Impl rule__WhereCondition__Group__2 )
            // InternalShardML.g:2625:2: rule__WhereCondition__Group__1__Impl rule__WhereCondition__Group__2
            {
            pushFollow(FOLLOW_31);
            rule__WhereCondition__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__WhereCondition__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WhereCondition__Group__1"


    // $ANTLR start "rule__WhereCondition__Group__1__Impl"
    // InternalShardML.g:2632:1: rule__WhereCondition__Group__1__Impl : ( ( rule__WhereCondition__OpAssignment_1 ) ) ;
    public final void rule__WhereCondition__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2636:1: ( ( ( rule__WhereCondition__OpAssignment_1 ) ) )
            // InternalShardML.g:2637:1: ( ( rule__WhereCondition__OpAssignment_1 ) )
            {
            // InternalShardML.g:2637:1: ( ( rule__WhereCondition__OpAssignment_1 ) )
            // InternalShardML.g:2638:2: ( rule__WhereCondition__OpAssignment_1 )
            {
             before(grammarAccess.getWhereConditionAccess().getOpAssignment_1()); 
            // InternalShardML.g:2639:2: ( rule__WhereCondition__OpAssignment_1 )
            // InternalShardML.g:2639:3: rule__WhereCondition__OpAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__WhereCondition__OpAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getWhereConditionAccess().getOpAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WhereCondition__Group__1__Impl"


    // $ANTLR start "rule__WhereCondition__Group__2"
    // InternalShardML.g:2647:1: rule__WhereCondition__Group__2 : rule__WhereCondition__Group__2__Impl ;
    public final void rule__WhereCondition__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2651:1: ( rule__WhereCondition__Group__2__Impl )
            // InternalShardML.g:2652:2: rule__WhereCondition__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__WhereCondition__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WhereCondition__Group__2"


    // $ANTLR start "rule__WhereCondition__Group__2__Impl"
    // InternalShardML.g:2658:1: rule__WhereCondition__Group__2__Impl : ( '?' ) ;
    public final void rule__WhereCondition__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2662:1: ( ( '?' ) )
            // InternalShardML.g:2663:1: ( '?' )
            {
            // InternalShardML.g:2663:1: ( '?' )
            // InternalShardML.g:2664:2: '?'
            {
             before(grammarAccess.getWhereConditionAccess().getQuestionMarkKeyword_2()); 
            match(input,53,FOLLOW_2); 
             after(grammarAccess.getWhereConditionAccess().getQuestionMarkKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WhereCondition__Group__2__Impl"


    // $ANTLR start "rule__SortClause__Group__0"
    // InternalShardML.g:2674:1: rule__SortClause__Group__0 : rule__SortClause__Group__0__Impl rule__SortClause__Group__1 ;
    public final void rule__SortClause__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2678:1: ( rule__SortClause__Group__0__Impl rule__SortClause__Group__1 )
            // InternalShardML.g:2679:2: rule__SortClause__Group__0__Impl rule__SortClause__Group__1
            {
            pushFollow(FOLLOW_32);
            rule__SortClause__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__SortClause__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SortClause__Group__0"


    // $ANTLR start "rule__SortClause__Group__0__Impl"
    // InternalShardML.g:2686:1: rule__SortClause__Group__0__Impl : ( ( rule__SortClause__FieldAssignment_0 ) ) ;
    public final void rule__SortClause__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2690:1: ( ( ( rule__SortClause__FieldAssignment_0 ) ) )
            // InternalShardML.g:2691:1: ( ( rule__SortClause__FieldAssignment_0 ) )
            {
            // InternalShardML.g:2691:1: ( ( rule__SortClause__FieldAssignment_0 ) )
            // InternalShardML.g:2692:2: ( rule__SortClause__FieldAssignment_0 )
            {
             before(grammarAccess.getSortClauseAccess().getFieldAssignment_0()); 
            // InternalShardML.g:2693:2: ( rule__SortClause__FieldAssignment_0 )
            // InternalShardML.g:2693:3: rule__SortClause__FieldAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__SortClause__FieldAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getSortClauseAccess().getFieldAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SortClause__Group__0__Impl"


    // $ANTLR start "rule__SortClause__Group__1"
    // InternalShardML.g:2701:1: rule__SortClause__Group__1 : rule__SortClause__Group__1__Impl ;
    public final void rule__SortClause__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2705:1: ( rule__SortClause__Group__1__Impl )
            // InternalShardML.g:2706:2: rule__SortClause__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__SortClause__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SortClause__Group__1"


    // $ANTLR start "rule__SortClause__Group__1__Impl"
    // InternalShardML.g:2712:1: rule__SortClause__Group__1__Impl : ( ( rule__SortClause__OrderAssignment_1 ) ) ;
    public final void rule__SortClause__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2716:1: ( ( ( rule__SortClause__OrderAssignment_1 ) ) )
            // InternalShardML.g:2717:1: ( ( rule__SortClause__OrderAssignment_1 ) )
            {
            // InternalShardML.g:2717:1: ( ( rule__SortClause__OrderAssignment_1 ) )
            // InternalShardML.g:2718:2: ( rule__SortClause__OrderAssignment_1 )
            {
             before(grammarAccess.getSortClauseAccess().getOrderAssignment_1()); 
            // InternalShardML.g:2719:2: ( rule__SortClause__OrderAssignment_1 )
            // InternalShardML.g:2719:3: rule__SortClause__OrderAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__SortClause__OrderAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getSortClauseAccess().getOrderAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SortClause__Group__1__Impl"


    // $ANTLR start "rule__Model__ImportsAssignment_0"
    // InternalShardML.g:2728:1: rule__Model__ImportsAssignment_0 : ( ruleImport ) ;
    public final void rule__Model__ImportsAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2732:1: ( ( ruleImport ) )
            // InternalShardML.g:2733:2: ( ruleImport )
            {
            // InternalShardML.g:2733:2: ( ruleImport )
            // InternalShardML.g:2734:3: ruleImport
            {
             before(grammarAccess.getModelAccess().getImportsImportParserRuleCall_0_0()); 
            pushFollow(FOLLOW_2);
            ruleImport();

            state._fsp--;

             after(grammarAccess.getModelAccess().getImportsImportParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__ImportsAssignment_0"


    // $ANTLR start "rule__Model__DbAssignment_1"
    // InternalShardML.g:2743:1: rule__Model__DbAssignment_1 : ( ruleDatabase ) ;
    public final void rule__Model__DbAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2747:1: ( ( ruleDatabase ) )
            // InternalShardML.g:2748:2: ( ruleDatabase )
            {
            // InternalShardML.g:2748:2: ( ruleDatabase )
            // InternalShardML.g:2749:3: ruleDatabase
            {
             before(grammarAccess.getModelAccess().getDbDatabaseParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleDatabase();

            state._fsp--;

             after(grammarAccess.getModelAccess().getDbDatabaseParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__DbAssignment_1"


    // $ANTLR start "rule__Import__ImportURIAssignment_1"
    // InternalShardML.g:2758:1: rule__Import__ImportURIAssignment_1 : ( RULE_STRING ) ;
    public final void rule__Import__ImportURIAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2762:1: ( ( RULE_STRING ) )
            // InternalShardML.g:2763:2: ( RULE_STRING )
            {
            // InternalShardML.g:2763:2: ( RULE_STRING )
            // InternalShardML.g:2764:3: RULE_STRING
            {
             before(grammarAccess.getImportAccess().getImportURISTRINGTerminalRuleCall_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getImportAccess().getImportURISTRINGTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Import__ImportURIAssignment_1"


    // $ANTLR start "rule__Database__NameAssignment_1"
    // InternalShardML.g:2773:1: rule__Database__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__Database__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2777:1: ( ( RULE_ID ) )
            // InternalShardML.g:2778:2: ( RULE_ID )
            {
            // InternalShardML.g:2778:2: ( RULE_ID )
            // InternalShardML.g:2779:3: RULE_ID
            {
             before(grammarAccess.getDatabaseAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getDatabaseAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__NameAssignment_1"


    // $ANTLR start "rule__Database__TypeAssignment_4"
    // InternalShardML.g:2788:1: rule__Database__TypeAssignment_4 : ( ruleDatabaseType ) ;
    public final void rule__Database__TypeAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2792:1: ( ( ruleDatabaseType ) )
            // InternalShardML.g:2793:2: ( ruleDatabaseType )
            {
            // InternalShardML.g:2793:2: ( ruleDatabaseType )
            // InternalShardML.g:2794:3: ruleDatabaseType
            {
             before(grammarAccess.getDatabaseAccess().getTypeDatabaseTypeEnumRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleDatabaseType();

            state._fsp--;

             after(grammarAccess.getDatabaseAccess().getTypeDatabaseTypeEnumRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__TypeAssignment_4"


    // $ANTLR start "rule__Database__Shard_declAssignment_5"
    // InternalShardML.g:2803:1: rule__Database__Shard_declAssignment_5 : ( ruleShardDecl ) ;
    public final void rule__Database__Shard_declAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2807:1: ( ( ruleShardDecl ) )
            // InternalShardML.g:2808:2: ( ruleShardDecl )
            {
            // InternalShardML.g:2808:2: ( ruleShardDecl )
            // InternalShardML.g:2809:3: ruleShardDecl
            {
             before(grammarAccess.getDatabaseAccess().getShard_declShardDeclParserRuleCall_5_0()); 
            pushFollow(FOLLOW_2);
            ruleShardDecl();

            state._fsp--;

             after(grammarAccess.getDatabaseAccess().getShard_declShardDeclParserRuleCall_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__Shard_declAssignment_5"


    // $ANTLR start "rule__Database__Route_rulesAssignment_6"
    // InternalShardML.g:2818:1: rule__Database__Route_rulesAssignment_6 : ( ruleRouteRule ) ;
    public final void rule__Database__Route_rulesAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2822:1: ( ( ruleRouteRule ) )
            // InternalShardML.g:2823:2: ( ruleRouteRule )
            {
            // InternalShardML.g:2823:2: ( ruleRouteRule )
            // InternalShardML.g:2824:3: ruleRouteRule
            {
             before(grammarAccess.getDatabaseAccess().getRoute_rulesRouteRuleParserRuleCall_6_0()); 
            pushFollow(FOLLOW_2);
            ruleRouteRule();

            state._fsp--;

             after(grammarAccess.getDatabaseAccess().getRoute_rulesRouteRuleParserRuleCall_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__Route_rulesAssignment_6"


    // $ANTLR start "rule__Database__RelationshipsAssignment_7"
    // InternalShardML.g:2833:1: rule__Database__RelationshipsAssignment_7 : ( ruleRelationship ) ;
    public final void rule__Database__RelationshipsAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2837:1: ( ( ruleRelationship ) )
            // InternalShardML.g:2838:2: ( ruleRelationship )
            {
            // InternalShardML.g:2838:2: ( ruleRelationship )
            // InternalShardML.g:2839:3: ruleRelationship
            {
             before(grammarAccess.getDatabaseAccess().getRelationshipsRelationshipParserRuleCall_7_0()); 
            pushFollow(FOLLOW_2);
            ruleRelationship();

            state._fsp--;

             after(grammarAccess.getDatabaseAccess().getRelationshipsRelationshipParserRuleCall_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Database__RelationshipsAssignment_7"


    // $ANTLR start "rule__HasMany__SubjectAssignment_0"
    // InternalShardML.g:2848:1: rule__HasMany__SubjectAssignment_0 : ( ( RULE_ID ) ) ;
    public final void rule__HasMany__SubjectAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2852:1: ( ( ( RULE_ID ) ) )
            // InternalShardML.g:2853:2: ( ( RULE_ID ) )
            {
            // InternalShardML.g:2853:2: ( ( RULE_ID ) )
            // InternalShardML.g:2854:3: ( RULE_ID )
            {
             before(grammarAccess.getHasManyAccess().getSubjectTableCrossReference_0_0()); 
            // InternalShardML.g:2855:3: ( RULE_ID )
            // InternalShardML.g:2856:4: RULE_ID
            {
             before(grammarAccess.getHasManyAccess().getSubjectTableIDTerminalRuleCall_0_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getHasManyAccess().getSubjectTableIDTerminalRuleCall_0_0_1()); 

            }

             after(grammarAccess.getHasManyAccess().getSubjectTableCrossReference_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HasMany__SubjectAssignment_0"


    // $ANTLR start "rule__HasMany__ManyAssignment_2"
    // InternalShardML.g:2867:1: rule__HasMany__ManyAssignment_2 : ( ( RULE_ID ) ) ;
    public final void rule__HasMany__ManyAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2871:1: ( ( ( RULE_ID ) ) )
            // InternalShardML.g:2872:2: ( ( RULE_ID ) )
            {
            // InternalShardML.g:2872:2: ( ( RULE_ID ) )
            // InternalShardML.g:2873:3: ( RULE_ID )
            {
             before(grammarAccess.getHasManyAccess().getManyTableCrossReference_2_0()); 
            // InternalShardML.g:2874:3: ( RULE_ID )
            // InternalShardML.g:2875:4: RULE_ID
            {
             before(grammarAccess.getHasManyAccess().getManyTableIDTerminalRuleCall_2_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getHasManyAccess().getManyTableIDTerminalRuleCall_2_0_1()); 

            }

             after(grammarAccess.getHasManyAccess().getManyTableCrossReference_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HasMany__ManyAssignment_2"


    // $ANTLR start "rule__HasOne__SubjectAssignment_0"
    // InternalShardML.g:2886:1: rule__HasOne__SubjectAssignment_0 : ( ( RULE_ID ) ) ;
    public final void rule__HasOne__SubjectAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2890:1: ( ( ( RULE_ID ) ) )
            // InternalShardML.g:2891:2: ( ( RULE_ID ) )
            {
            // InternalShardML.g:2891:2: ( ( RULE_ID ) )
            // InternalShardML.g:2892:3: ( RULE_ID )
            {
             before(grammarAccess.getHasOneAccess().getSubjectTableCrossReference_0_0()); 
            // InternalShardML.g:2893:3: ( RULE_ID )
            // InternalShardML.g:2894:4: RULE_ID
            {
             before(grammarAccess.getHasOneAccess().getSubjectTableIDTerminalRuleCall_0_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getHasOneAccess().getSubjectTableIDTerminalRuleCall_0_0_1()); 

            }

             after(grammarAccess.getHasOneAccess().getSubjectTableCrossReference_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HasOne__SubjectAssignment_0"


    // $ANTLR start "rule__HasOne__OneAssignment_2"
    // InternalShardML.g:2905:1: rule__HasOne__OneAssignment_2 : ( ( RULE_ID ) ) ;
    public final void rule__HasOne__OneAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2909:1: ( ( ( RULE_ID ) ) )
            // InternalShardML.g:2910:2: ( ( RULE_ID ) )
            {
            // InternalShardML.g:2910:2: ( ( RULE_ID ) )
            // InternalShardML.g:2911:3: ( RULE_ID )
            {
             before(grammarAccess.getHasOneAccess().getOneTableCrossReference_2_0()); 
            // InternalShardML.g:2912:3: ( RULE_ID )
            // InternalShardML.g:2913:4: RULE_ID
            {
             before(grammarAccess.getHasOneAccess().getOneTableIDTerminalRuleCall_2_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getHasOneAccess().getOneTableIDTerminalRuleCall_2_0_1()); 

            }

             after(grammarAccess.getHasOneAccess().getOneTableCrossReference_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__HasOne__OneAssignment_2"


    // $ANTLR start "rule__BelongsTo__SubjectAssignment_0"
    // InternalShardML.g:2924:1: rule__BelongsTo__SubjectAssignment_0 : ( ( RULE_ID ) ) ;
    public final void rule__BelongsTo__SubjectAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2928:1: ( ( ( RULE_ID ) ) )
            // InternalShardML.g:2929:2: ( ( RULE_ID ) )
            {
            // InternalShardML.g:2929:2: ( ( RULE_ID ) )
            // InternalShardML.g:2930:3: ( RULE_ID )
            {
             before(grammarAccess.getBelongsToAccess().getSubjectTableCrossReference_0_0()); 
            // InternalShardML.g:2931:3: ( RULE_ID )
            // InternalShardML.g:2932:4: RULE_ID
            {
             before(grammarAccess.getBelongsToAccess().getSubjectTableIDTerminalRuleCall_0_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getBelongsToAccess().getSubjectTableIDTerminalRuleCall_0_0_1()); 

            }

             after(grammarAccess.getBelongsToAccess().getSubjectTableCrossReference_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BelongsTo__SubjectAssignment_0"


    // $ANTLR start "rule__BelongsTo__BelongsAssignment_2"
    // InternalShardML.g:2943:1: rule__BelongsTo__BelongsAssignment_2 : ( ( RULE_ID ) ) ;
    public final void rule__BelongsTo__BelongsAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2947:1: ( ( ( RULE_ID ) ) )
            // InternalShardML.g:2948:2: ( ( RULE_ID ) )
            {
            // InternalShardML.g:2948:2: ( ( RULE_ID ) )
            // InternalShardML.g:2949:3: ( RULE_ID )
            {
             before(grammarAccess.getBelongsToAccess().getBelongsTableCrossReference_2_0()); 
            // InternalShardML.g:2950:3: ( RULE_ID )
            // InternalShardML.g:2951:4: RULE_ID
            {
             before(grammarAccess.getBelongsToAccess().getBelongsTableIDTerminalRuleCall_2_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getBelongsToAccess().getBelongsTableIDTerminalRuleCall_2_0_1()); 

            }

             after(grammarAccess.getBelongsToAccess().getBelongsTableCrossReference_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__BelongsTo__BelongsAssignment_2"


    // $ANTLR start "rule__ShardDecl__EntityAssignment_1"
    // InternalShardML.g:2962:1: rule__ShardDecl__EntityAssignment_1 : ( ( RULE_ID ) ) ;
    public final void rule__ShardDecl__EntityAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2966:1: ( ( ( RULE_ID ) ) )
            // InternalShardML.g:2967:2: ( ( RULE_ID ) )
            {
            // InternalShardML.g:2967:2: ( ( RULE_ID ) )
            // InternalShardML.g:2968:3: ( RULE_ID )
            {
             before(grammarAccess.getShardDeclAccess().getEntityTableCrossReference_1_0()); 
            // InternalShardML.g:2969:3: ( RULE_ID )
            // InternalShardML.g:2970:4: RULE_ID
            {
             before(grammarAccess.getShardDeclAccess().getEntityTableIDTerminalRuleCall_1_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getShardDeclAccess().getEntityTableIDTerminalRuleCall_1_0_1()); 

            }

             after(grammarAccess.getShardDeclAccess().getEntityTableCrossReference_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__EntityAssignment_1"


    // $ANTLR start "rule__ShardDecl__StrategyAssignment_4"
    // InternalShardML.g:2981:1: rule__ShardDecl__StrategyAssignment_4 : ( ruleStrategy ) ;
    public final void rule__ShardDecl__StrategyAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:2985:1: ( ( ruleStrategy ) )
            // InternalShardML.g:2986:2: ( ruleStrategy )
            {
            // InternalShardML.g:2986:2: ( ruleStrategy )
            // InternalShardML.g:2987:3: ruleStrategy
            {
             before(grammarAccess.getShardDeclAccess().getStrategyStrategyEnumRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleStrategy();

            state._fsp--;

             after(grammarAccess.getShardDeclAccess().getStrategyStrategyEnumRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__StrategyAssignment_4"


    // $ANTLR start "rule__ShardDecl__IdAssignment_5_1"
    // InternalShardML.g:2996:1: rule__ShardDecl__IdAssignment_5_1 : ( ( RULE_ID ) ) ;
    public final void rule__ShardDecl__IdAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3000:1: ( ( ( RULE_ID ) ) )
            // InternalShardML.g:3001:2: ( ( RULE_ID ) )
            {
            // InternalShardML.g:3001:2: ( ( RULE_ID ) )
            // InternalShardML.g:3002:3: ( RULE_ID )
            {
             before(grammarAccess.getShardDeclAccess().getIdColumnCrossReference_5_1_0()); 
            // InternalShardML.g:3003:3: ( RULE_ID )
            // InternalShardML.g:3004:4: RULE_ID
            {
             before(grammarAccess.getShardDeclAccess().getIdColumnIDTerminalRuleCall_5_1_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getShardDeclAccess().getIdColumnIDTerminalRuleCall_5_1_0_1()); 

            }

             after(grammarAccess.getShardDeclAccess().getIdColumnCrossReference_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__IdAssignment_5_1"


    // $ANTLR start "rule__ShardDecl__BucketsAssignment_6_1"
    // InternalShardML.g:3015:1: rule__ShardDecl__BucketsAssignment_6_1 : ( RULE_INT ) ;
    public final void rule__ShardDecl__BucketsAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3019:1: ( ( RULE_INT ) )
            // InternalShardML.g:3020:2: ( RULE_INT )
            {
            // InternalShardML.g:3020:2: ( RULE_INT )
            // InternalShardML.g:3021:3: RULE_INT
            {
             before(grammarAccess.getShardDeclAccess().getBucketsINTTerminalRuleCall_6_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getShardDeclAccess().getBucketsINTTerminalRuleCall_6_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__BucketsAssignment_6_1"


    // $ANTLR start "rule__ShardDecl__ColocateAssignment_7_1"
    // InternalShardML.g:3030:1: rule__ShardDecl__ColocateAssignment_7_1 : ( ( RULE_ID ) ) ;
    public final void rule__ShardDecl__ColocateAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3034:1: ( ( ( RULE_ID ) ) )
            // InternalShardML.g:3035:2: ( ( RULE_ID ) )
            {
            // InternalShardML.g:3035:2: ( ( RULE_ID ) )
            // InternalShardML.g:3036:3: ( RULE_ID )
            {
             before(grammarAccess.getShardDeclAccess().getColocateTableCrossReference_7_1_0()); 
            // InternalShardML.g:3037:3: ( RULE_ID )
            // InternalShardML.g:3038:4: RULE_ID
            {
             before(grammarAccess.getShardDeclAccess().getColocateTableIDTerminalRuleCall_7_1_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getShardDeclAccess().getColocateTableIDTerminalRuleCall_7_1_0_1()); 

            }

             after(grammarAccess.getShardDeclAccess().getColocateTableCrossReference_7_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ShardDecl__ColocateAssignment_7_1"


    // $ANTLR start "rule__RouteRule__EntityAssignment_1"
    // InternalShardML.g:3049:1: rule__RouteRule__EntityAssignment_1 : ( ( RULE_ID ) ) ;
    public final void rule__RouteRule__EntityAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3053:1: ( ( ( RULE_ID ) ) )
            // InternalShardML.g:3054:2: ( ( RULE_ID ) )
            {
            // InternalShardML.g:3054:2: ( ( RULE_ID ) )
            // InternalShardML.g:3055:3: ( RULE_ID )
            {
             before(grammarAccess.getRouteRuleAccess().getEntityTableCrossReference_1_0()); 
            // InternalShardML.g:3056:3: ( RULE_ID )
            // InternalShardML.g:3057:4: RULE_ID
            {
             before(grammarAccess.getRouteRuleAccess().getEntityTableIDTerminalRuleCall_1_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getRouteRuleAccess().getEntityTableIDTerminalRuleCall_1_0_1()); 

            }

             after(grammarAccess.getRouteRuleAccess().getEntityTableCrossReference_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__EntityAssignment_1"


    // $ANTLR start "rule__RouteRule__PolicyAssignment_4"
    // InternalShardML.g:3068:1: rule__RouteRule__PolicyAssignment_4 : ( rulePolicy ) ;
    public final void rule__RouteRule__PolicyAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3072:1: ( ( rulePolicy ) )
            // InternalShardML.g:3073:2: ( rulePolicy )
            {
            // InternalShardML.g:3073:2: ( rulePolicy )
            // InternalShardML.g:3074:3: rulePolicy
            {
             before(grammarAccess.getRouteRuleAccess().getPolicyPolicyEnumRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            rulePolicy();

            state._fsp--;

             after(grammarAccess.getRouteRuleAccess().getPolicyPolicyEnumRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__PolicyAssignment_4"


    // $ANTLR start "rule__RouteRule__CondtionsAssignment_5_1"
    // InternalShardML.g:3083:1: rule__RouteRule__CondtionsAssignment_5_1 : ( RULE_INT ) ;
    public final void rule__RouteRule__CondtionsAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3087:1: ( ( RULE_INT ) )
            // InternalShardML.g:3088:2: ( RULE_INT )
            {
            // InternalShardML.g:3088:2: ( RULE_INT )
            // InternalShardML.g:3089:3: RULE_INT
            {
             before(grammarAccess.getRouteRuleAccess().getCondtionsINTTerminalRuleCall_5_1_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getRouteRuleAccess().getCondtionsINTTerminalRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__CondtionsAssignment_5_1"


    // $ANTLR start "rule__RouteRule__QueriesAssignment_6"
    // InternalShardML.g:3098:1: rule__RouteRule__QueriesAssignment_6 : ( ruleQueryPattern ) ;
    public final void rule__RouteRule__QueriesAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3102:1: ( ( ruleQueryPattern ) )
            // InternalShardML.g:3103:2: ( ruleQueryPattern )
            {
            // InternalShardML.g:3103:2: ( ruleQueryPattern )
            // InternalShardML.g:3104:3: ruleQueryPattern
            {
             before(grammarAccess.getRouteRuleAccess().getQueriesQueryPatternParserRuleCall_6_0()); 
            pushFollow(FOLLOW_2);
            ruleQueryPattern();

            state._fsp--;

             after(grammarAccess.getRouteRuleAccess().getQueriesQueryPatternParserRuleCall_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__RouteRule__QueriesAssignment_6"


    // $ANTLR start "rule__QueryPattern__NameAssignment_1"
    // InternalShardML.g:3113:1: rule__QueryPattern__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__QueryPattern__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3117:1: ( ( RULE_ID ) )
            // InternalShardML.g:3118:2: ( RULE_ID )
            {
            // InternalShardML.g:3118:2: ( RULE_ID )
            // InternalShardML.g:3119:3: RULE_ID
            {
             before(grammarAccess.getQueryPatternAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getQueryPatternAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__NameAssignment_1"


    // $ANTLR start "rule__QueryPattern__QueryTypeAssignment_4"
    // InternalShardML.g:3128:1: rule__QueryPattern__QueryTypeAssignment_4 : ( ruleQueryType ) ;
    public final void rule__QueryPattern__QueryTypeAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3132:1: ( ( ruleQueryType ) )
            // InternalShardML.g:3133:2: ( ruleQueryType )
            {
            // InternalShardML.g:3133:2: ( ruleQueryType )
            // InternalShardML.g:3134:3: ruleQueryType
            {
             before(grammarAccess.getQueryPatternAccess().getQueryTypeQueryTypeEnumRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleQueryType();

            state._fsp--;

             after(grammarAccess.getQueryPatternAccess().getQueryTypeQueryTypeEnumRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__QueryTypeAssignment_4"


    // $ANTLR start "rule__QueryPattern__WhereAssignment_5_1"
    // InternalShardML.g:3143:1: rule__QueryPattern__WhereAssignment_5_1 : ( ruleWhereClause ) ;
    public final void rule__QueryPattern__WhereAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3147:1: ( ( ruleWhereClause ) )
            // InternalShardML.g:3148:2: ( ruleWhereClause )
            {
            // InternalShardML.g:3148:2: ( ruleWhereClause )
            // InternalShardML.g:3149:3: ruleWhereClause
            {
             before(grammarAccess.getQueryPatternAccess().getWhereWhereClauseParserRuleCall_5_1_0()); 
            pushFollow(FOLLOW_2);
            ruleWhereClause();

            state._fsp--;

             after(grammarAccess.getQueryPatternAccess().getWhereWhereClauseParserRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__WhereAssignment_5_1"


    // $ANTLR start "rule__QueryPattern__SortAssignment_6_1"
    // InternalShardML.g:3158:1: rule__QueryPattern__SortAssignment_6_1 : ( ruleSortClause ) ;
    public final void rule__QueryPattern__SortAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3162:1: ( ( ruleSortClause ) )
            // InternalShardML.g:3163:2: ( ruleSortClause )
            {
            // InternalShardML.g:3163:2: ( ruleSortClause )
            // InternalShardML.g:3164:3: ruleSortClause
            {
             before(grammarAccess.getQueryPatternAccess().getSortSortClauseParserRuleCall_6_1_0()); 
            pushFollow(FOLLOW_2);
            ruleSortClause();

            state._fsp--;

             after(grammarAccess.getQueryPatternAccess().getSortSortClauseParserRuleCall_6_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__SortAssignment_6_1"


    // $ANTLR start "rule__QueryPattern__GroupAssignment_7_1"
    // InternalShardML.g:3173:1: rule__QueryPattern__GroupAssignment_7_1 : ( ruleGroupBy ) ;
    public final void rule__QueryPattern__GroupAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3177:1: ( ( ruleGroupBy ) )
            // InternalShardML.g:3178:2: ( ruleGroupBy )
            {
            // InternalShardML.g:3178:2: ( ruleGroupBy )
            // InternalShardML.g:3179:3: ruleGroupBy
            {
             before(grammarAccess.getQueryPatternAccess().getGroupGroupByParserRuleCall_7_1_0()); 
            pushFollow(FOLLOW_2);
            ruleGroupBy();

            state._fsp--;

             after(grammarAccess.getQueryPatternAccess().getGroupGroupByParserRuleCall_7_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__GroupAssignment_7_1"


    // $ANTLR start "rule__QueryPattern__DistinctAssignment_8_1"
    // InternalShardML.g:3188:1: rule__QueryPattern__DistinctAssignment_8_1 : ( ( 'true' ) ) ;
    public final void rule__QueryPattern__DistinctAssignment_8_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3192:1: ( ( ( 'true' ) ) )
            // InternalShardML.g:3193:2: ( ( 'true' ) )
            {
            // InternalShardML.g:3193:2: ( ( 'true' ) )
            // InternalShardML.g:3194:3: ( 'true' )
            {
             before(grammarAccess.getQueryPatternAccess().getDistinctTrueKeyword_8_1_0()); 
            // InternalShardML.g:3195:3: ( 'true' )
            // InternalShardML.g:3196:4: 'true'
            {
             before(grammarAccess.getQueryPatternAccess().getDistinctTrueKeyword_8_1_0()); 
            match(input,54,FOLLOW_2); 
             after(grammarAccess.getQueryPatternAccess().getDistinctTrueKeyword_8_1_0()); 

            }

             after(grammarAccess.getQueryPatternAccess().getDistinctTrueKeyword_8_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__QueryPattern__DistinctAssignment_8_1"


    // $ANTLR start "rule__WhereClause__FirstAssignment_0"
    // InternalShardML.g:3207:1: rule__WhereClause__FirstAssignment_0 : ( ruleWhereCondition ) ;
    public final void rule__WhereClause__FirstAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3211:1: ( ( ruleWhereCondition ) )
            // InternalShardML.g:3212:2: ( ruleWhereCondition )
            {
            // InternalShardML.g:3212:2: ( ruleWhereCondition )
            // InternalShardML.g:3213:3: ruleWhereCondition
            {
             before(grammarAccess.getWhereClauseAccess().getFirstWhereConditionParserRuleCall_0_0()); 
            pushFollow(FOLLOW_2);
            ruleWhereCondition();

            state._fsp--;

             after(grammarAccess.getWhereClauseAccess().getFirstWhereConditionParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WhereClause__FirstAssignment_0"


    // $ANTLR start "rule__WhereClause__LinkAssignment_1_0"
    // InternalShardML.g:3222:1: rule__WhereClause__LinkAssignment_1_0 : ( ruleLink ) ;
    public final void rule__WhereClause__LinkAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3226:1: ( ( ruleLink ) )
            // InternalShardML.g:3227:2: ( ruleLink )
            {
            // InternalShardML.g:3227:2: ( ruleLink )
            // InternalShardML.g:3228:3: ruleLink
            {
             before(grammarAccess.getWhereClauseAccess().getLinkLinkEnumRuleCall_1_0_0()); 
            pushFollow(FOLLOW_2);
            ruleLink();

            state._fsp--;

             after(grammarAccess.getWhereClauseAccess().getLinkLinkEnumRuleCall_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WhereClause__LinkAssignment_1_0"


    // $ANTLR start "rule__WhereClause__RestAssignment_1_1"
    // InternalShardML.g:3237:1: rule__WhereClause__RestAssignment_1_1 : ( ruleWhereCondition ) ;
    public final void rule__WhereClause__RestAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3241:1: ( ( ruleWhereCondition ) )
            // InternalShardML.g:3242:2: ( ruleWhereCondition )
            {
            // InternalShardML.g:3242:2: ( ruleWhereCondition )
            // InternalShardML.g:3243:3: ruleWhereCondition
            {
             before(grammarAccess.getWhereClauseAccess().getRestWhereConditionParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleWhereCondition();

            state._fsp--;

             after(grammarAccess.getWhereClauseAccess().getRestWhereConditionParserRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WhereClause__RestAssignment_1_1"


    // $ANTLR start "rule__WhereCondition__FieldAssignment_0"
    // InternalShardML.g:3252:1: rule__WhereCondition__FieldAssignment_0 : ( ( RULE_ID ) ) ;
    public final void rule__WhereCondition__FieldAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3256:1: ( ( ( RULE_ID ) ) )
            // InternalShardML.g:3257:2: ( ( RULE_ID ) )
            {
            // InternalShardML.g:3257:2: ( ( RULE_ID ) )
            // InternalShardML.g:3258:3: ( RULE_ID )
            {
             before(grammarAccess.getWhereConditionAccess().getFieldColumnCrossReference_0_0()); 
            // InternalShardML.g:3259:3: ( RULE_ID )
            // InternalShardML.g:3260:4: RULE_ID
            {
             before(grammarAccess.getWhereConditionAccess().getFieldColumnIDTerminalRuleCall_0_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getWhereConditionAccess().getFieldColumnIDTerminalRuleCall_0_0_1()); 

            }

             after(grammarAccess.getWhereConditionAccess().getFieldColumnCrossReference_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WhereCondition__FieldAssignment_0"


    // $ANTLR start "rule__WhereCondition__OpAssignment_1"
    // InternalShardML.g:3271:1: rule__WhereCondition__OpAssignment_1 : ( ruleOperator ) ;
    public final void rule__WhereCondition__OpAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3275:1: ( ( ruleOperator ) )
            // InternalShardML.g:3276:2: ( ruleOperator )
            {
            // InternalShardML.g:3276:2: ( ruleOperator )
            // InternalShardML.g:3277:3: ruleOperator
            {
             before(grammarAccess.getWhereConditionAccess().getOpOperatorEnumRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleOperator();

            state._fsp--;

             after(grammarAccess.getWhereConditionAccess().getOpOperatorEnumRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__WhereCondition__OpAssignment_1"


    // $ANTLR start "rule__SortClause__FieldAssignment_0"
    // InternalShardML.g:3286:1: rule__SortClause__FieldAssignment_0 : ( ( RULE_ID ) ) ;
    public final void rule__SortClause__FieldAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3290:1: ( ( ( RULE_ID ) ) )
            // InternalShardML.g:3291:2: ( ( RULE_ID ) )
            {
            // InternalShardML.g:3291:2: ( ( RULE_ID ) )
            // InternalShardML.g:3292:3: ( RULE_ID )
            {
             before(grammarAccess.getSortClauseAccess().getFieldColumnCrossReference_0_0()); 
            // InternalShardML.g:3293:3: ( RULE_ID )
            // InternalShardML.g:3294:4: RULE_ID
            {
             before(grammarAccess.getSortClauseAccess().getFieldColumnIDTerminalRuleCall_0_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getSortClauseAccess().getFieldColumnIDTerminalRuleCall_0_0_1()); 

            }

             after(grammarAccess.getSortClauseAccess().getFieldColumnCrossReference_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SortClause__FieldAssignment_0"


    // $ANTLR start "rule__SortClause__OrderAssignment_1"
    // InternalShardML.g:3305:1: rule__SortClause__OrderAssignment_1 : ( ruleSortOrder ) ;
    public final void rule__SortClause__OrderAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3309:1: ( ( ruleSortOrder ) )
            // InternalShardML.g:3310:2: ( ruleSortOrder )
            {
            // InternalShardML.g:3310:2: ( ruleSortOrder )
            // InternalShardML.g:3311:3: ruleSortOrder
            {
             before(grammarAccess.getSortClauseAccess().getOrderSortOrderEnumRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleSortOrder();

            state._fsp--;

             after(grammarAccess.getSortClauseAccess().getOrderSortOrderEnumRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__SortClause__OrderAssignment_1"


    // $ANTLR start "rule__GroupBy__FieldAssignment"
    // InternalShardML.g:3320:1: rule__GroupBy__FieldAssignment : ( ( RULE_ID ) ) ;
    public final void rule__GroupBy__FieldAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalShardML.g:3324:1: ( ( ( RULE_ID ) ) )
            // InternalShardML.g:3325:2: ( ( RULE_ID ) )
            {
            // InternalShardML.g:3325:2: ( ( RULE_ID ) )
            // InternalShardML.g:3326:3: ( RULE_ID )
            {
             before(grammarAccess.getGroupByAccess().getFieldColumnCrossReference_0()); 
            // InternalShardML.g:3327:3: ( RULE_ID )
            // InternalShardML.g:3328:4: RULE_ID
            {
             before(grammarAccess.getGroupByAccess().getFieldColumnIDTerminalRuleCall_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getGroupByAccess().getFieldColumnIDTerminalRuleCall_0_1()); 

            }

             after(grammarAccess.getGroupByAccess().getFieldColumnCrossReference_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__GroupBy__FieldAssignment"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000211000000020L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000010000000002L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000200000000002L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x000000000000E000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x00001C1000000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000070000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0001801000000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000380000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x001E001000000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000030000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000030000002L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x000000000FC00000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x00000000C0000000L});

}
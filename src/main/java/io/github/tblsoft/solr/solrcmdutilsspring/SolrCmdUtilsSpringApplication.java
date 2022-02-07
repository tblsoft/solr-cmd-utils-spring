package io.github.tblsoft.solr.solrcmdutilsspring;

import de.tblsoft.solr.pipeline.PipelineExecuter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class SolrCmdUtilsSpringApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(SolrCmdUtilsSpringApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		List<String> pipelines = args.getOptionValues("pipelines");
		if(pipelines == null) {
			throw new IllegalArgumentException("No pipelines parameter is set.");
		}

		Map variables =
				args.getOptionNames().stream().collect(
						Collectors.toMap(
								s -> s,
								s -> args.getOptionValues(s).stream().findFirst().orElse(null)));
		for (String pipeline : pipelines) {
			PipelineExecuter pipelineExecuter = new PipelineExecuter(pipeline);
			pipelineExecuter.setPipelineVariables(variables);
			pipelineExecuter.execute();
		}

	}

}

package com.wds.tools.envers.cli.support;

import io.airlift.command.Cli;
import io.airlift.command.Cli.CliBuilder;

import java.util.HashSet;
import java.util.Set;

import com.wds.tools.envers.cli.support.command.InstallCommand;
import com.wds.tools.envers.cli.support.command.VersionCommand;

public class CommandLineBuilder {
	public static final CommandRunner build(final String[] args) {
		return new CommandRunner() {
			@Override
			public void run() {
				RunnerContext.set();
				ConcreteBuilder.build().parse(args).run();
				RunnerContext.unset();
			}
		};
	}

	public static interface CommandRunner {
		void run();
	}

	private static class ConcreteBuilder {
		public static Cli<Runnable> build() {
			Set<Class<? extends Runnable>> commands = new HashSet<Class<? extends Runnable>>();
			commands.add(DefaultCommand.class);
			commands.add(VersionCommand.class);
			commands.add(InstallCommand.class);

			CliBuilder<Runnable> builder = Cli.<Runnable> builder(RunnerContext.current().getCommandLineName())
					.withDescription("the stupid content tracker").withDefaultCommand(DefaultCommand.class)
					.withCommands(commands);

			return builder.build();
		}
	}
}
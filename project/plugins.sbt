logLevel := Level.Warn
resolvers += Resolver.url("grouptech-sbt-plugin-snapshots", url("https://repo.schbstd.com/nexus/content/repositories/sbt-plugin-snapshots/"))(Resolver.ivyStylePatterns)

resolvers += Resolver.url("grouptech-sbt-plugin-releases", url("https://repo.schbstd.com/nexus/content/repositories/sbt-plugin-releases/"))(Resolver.ivyStylePatterns)

credentials += Credentials(Path.userHome / ".sbt" / "ada-credentials")

addSbtPlugin("com.schibsted.sbt" % "sbt-ada" % "0.8.5")
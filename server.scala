import cats.effect.*
import org.typelevel.log4cats.slf4j.Slf4jLogger

import latis.catalog.FdmlCatalog
import latis.ops.OperationRegistry
import latis.server.Latis3ServerBuilder.*
import latis.service.dap2.Dap2Service
import latis.service.hapi.HapiService

object Server extends IOApp:

  override def run(args: List[String]): IO[ExitCode] =
    (for {
      logger <- Resource.eval(Slf4jLogger.create[IO])
      serverConf  <- Resource.eval(getServerConf)
      catalogConf <- Resource.eval(getCatalogConf)
      fdmlCat <- Resource.eval(
        FdmlCatalog.fromDirectory(catalogConf.dir, catalogConf.validate, OperationRegistry.default)
      )
      catalog = fdmlCat
      interfaces = List(
        "dap2" -> new Dap2Service(catalog, OperationRegistry.default),
        "hapi" -> new HapiService(catalog)
      )
      server <- mkServer(serverConf, defaultLandingPage, interfaces, logger)
    } yield server)
      .use(_ => IO.never)
      .as(ExitCode.Success)


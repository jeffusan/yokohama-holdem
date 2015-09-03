package yokohama.holdem

import akka.actor.PossiblyHarmful
import scopt.OptionParser

// CLI arguments are parsed to this class
case class ArgConf(action: String = "", playerName: String = "", playerCount: Int = -1, isUsage: Boolean = false)

trait Terminal {

  // possiblyharmful is just a marker
  sealed trait Command extends PossiblyHarmful

  // lists commands that can be received
  object Command {

    case object NoOperation extends Command

    case class Register(playerName: String, playerCount: Int) extends Command

    case object Shutdown extends Command

    case class Unknown(command: String, message: String) extends Command

    def apply(command: Array[String]): Command = CommandParser.parseAsCommand(command)

  }

  object CommandParser {

    val parser = new OptionParser[ArgConf]("yokohama-holdem") {

      // do not use help keyword because we don't want to exit
      opt[Unit]("help") text ("prints this usage message") action { (_, argConf) =>
        showUsage
        argConf.copy(isUsage = true)
      }

      // $> register -n testuser -c 5
      // register 5 users
      cmd("register") action { (_, argConf) =>
        argConf.copy(action = "register")
      } text("register is a command") children (
        opt[String]("name") abbr("n") action { (n,argConf) =>
          argConf.copy(playerName = n)
        } text("player name is a string"),
        opt[Int]("count") abbr("c") action { (c,argConf) =>
          argConf.copy(playerCount = c)
        } text("player count is a number")
      )

      // $> shutdown
      cmd("shutdown") action { (_, argConf) =>
        argConf.copy(action = "shutdown")
      } text("shutting down")

      checkConfig {
        case ArgConf("register", playerName, playerCount, _) if playerName.nonEmpty && playerCount > 0 => success
        case ArgConf("register", _, _, _) => failure("invalid register options")
        case _ => success
      }
    }

    def parseAsCommand(args: Array[String]): Command = {

      val optionConf = parser.parse(args, ArgConf())

      optionConf match {
        case Some(ArgConf(_, _, _, true)) =>
          Command.NoOperation
        case Some(ArgConf("register", playerName, playerCount, _)) =>
          Command.Register(playerName, playerCount)
        case Some(ArgConf("shutdown", _, _, _)) =>
          Command.Shutdown
        case Some(argConf) =>
          Command.Unknown("incomplete", "this looks like a valid argument, but something is not quite right")
        case None =>
          Command.NoOperation
      }
    }
  }
}

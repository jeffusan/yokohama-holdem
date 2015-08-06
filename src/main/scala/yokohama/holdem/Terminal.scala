package yokohama.holdem

import akka.actor.{ PossiblyHarmful, Props }
import scopt.OptionParser

// CLI arguments are parsed to this class
case class ArgConf(action: String = "", playerCount: Int = -1, playerName: String = "")

trait Terminal {

  // possiblyharmful is just a marker
  sealed trait Command extends PossiblyHarmful

  // lists commands that can be received
  object Command {

    case class Register(name: String, props: Props, count: Int = 1) extends Command

    case object Shutdown extends Command

    case class Unknown(command: String, message: String) extends Command

    def apply(command: Array[String]): Command =
      CommandParser.parseAsCommand(command)

  }

  object CommandParser {

    val parser = new OptionParser[ArgConf]("yokohama-holdem") {

      help("help") text("prints this usage message")

      // $> register -n testuser -c 5
      // register 5 users
      cmd("register") action { (_, argConf) =>
        argConf.copy(action = "register")
      } text("register is a command") children (
        opt[String]("register name") abbr("n") action { (n,argConf) =>
          argConf.copy(playerName = n)
        } text("playername is a string"),
        opt[Int]("playercount") abbr("c") action { (c,argConf) =>
          argConf.copy(playerCount = c)
        } text("playercount is a number"),
        checkConfig { argConf =>
          if(argConf.playerName != "" && argConf.playerCount != -1) {
            success
          } else {
            failure("invalid register options")
          }
        }
      )

      // $> shutdown
      cmd("shutdown") action { (_, argConf) =>
        argConf.copy(action = "shutdown")
      } text("shutting down")

    }

    def parseAsCommand(args: Array[String]): Command = {

      val optionConf = parser.parse(args, ArgConf())

      optionConf match {
          case Some(ArgConf("register", _, _)) =>
            val regConf = optionConf.get
            new Command.Register(regConf.playerName, Props(new Player()), regConf.playerCount)

          case Some(ArgConf("shutdown",_,_)) =>
            Command.Shutdown
          case Some(argConf) =>
            new Command.Unknown("incomplete", "this looks like a valid argument, but something is not quite right")
          case None =>
            new Command.Unknown("something", "something else")

      }
    }
  }
}

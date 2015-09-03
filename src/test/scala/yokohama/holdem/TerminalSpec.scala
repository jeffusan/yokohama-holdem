package yokohama.holdem

import java.io.ByteArrayOutputStream

import org.scalatest._

class TerminalSpec extends FlatSpec with MustMatchers {
  object t extends Terminal

  import t.Command._

  "CommandParser#parseAsCommand" should  "parse help option and return NoOperation" in {
    val os = new ByteArrayOutputStream
    Console.withOut(os) {
      t.CommandParser.parseAsCommand(Array("--help")) mustBe NoOperation
    }
    os.close()
  }
  it should "parse register command" in {
    t.CommandParser.parseAsCommand(Array("register", "-n", "p2", "-c", "3")) mustBe Register("p2", 3)
    t.CommandParser.parseAsCommand(Array("register", "--name", "p3", "--count", "4")) mustBe Register("p3", 4)
  }
  it should "parse shutdown command" in {
    t.CommandParser.parseAsCommand(Array("shutdown")) mustBe Shutdown
  }
  it should "return NoOperation when the arguments are invalid" in {
    val os = new ByteArrayOutputStream
    Console.withErr(os) {
      t.CommandParser.parseAsCommand(Array("")) mustBe NoOperation
      t.CommandParser.parseAsCommand(Array("register")) mustBe NoOperation
      t.CommandParser.parseAsCommand(Array("unknown-command")) mustBe NoOperation
      t.CommandParser.parseAsCommand(Array("unknown-command", "arg1", "arg2")) mustBe NoOperation
      t.CommandParser.parseAsCommand(Array("register", "-n")) mustBe NoOperation
      t.CommandParser.parseAsCommand(Array("register", "-n", "p2")) mustBe NoOperation
      t.CommandParser.parseAsCommand(Array("register", "-n", "p2", "-c")) mustBe NoOperation
    }
    os.close()
  }

}

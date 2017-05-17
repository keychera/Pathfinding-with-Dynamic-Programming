package controller;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Resource {

  public static Path getWorld(String string) {
    return Paths.get("res", string);
  }

}

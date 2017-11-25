package com.ph.powerfiler.util;

import java.io.File;
import java.io.FileFilter;

public class KeptCsvFileFilter implements FileFilter
{
  
  @Override
  public boolean accept(File file)
  {
    return !file.isDirectory() && !file.getName().contains(PowerfilerConstants.KEPT_EXTENSION) && file.getName().contains(PowerfilerConstants.TYPE_CSV);
  }
}

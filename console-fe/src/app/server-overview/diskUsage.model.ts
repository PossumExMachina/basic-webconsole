export class DiskUsage{
  public fileSystem: string;
  public fileSystemSize: string;
  public  used: string;
  public availabl: string;
  public capacity: string;
  public iused: string
  public  ifree: string;
  public mountedOn: string;


  constructor(fileSystem: string, fileSystemSize: string, used: string, availabl: string, capacity: string, iused: string, ifree: string, mountedOn: string) {
    this.fileSystem = fileSystem;
    this.fileSystemSize = fileSystemSize;
    this.used = used;
    this.availabl = availabl;
    this.capacity = capacity;
    this.iused = iused;
    this.ifree = ifree;
    this.mountedOn = mountedOn;
  }
}

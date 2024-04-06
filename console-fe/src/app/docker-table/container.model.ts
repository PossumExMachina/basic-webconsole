export class Container {
  public ID: string;
  public Image: string;
  public CreatedAt: string;
  public State: string;
  public Names: string;

  constructor(ID: string, Image: string, CreatedAt: string, State: string, Names: string) {
    this.ID = ID;
    this.Image = Image;
    this.CreatedAt = CreatedAt;
    this.State = State;
    this.Names = Names;
  }
}

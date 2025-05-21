package models;

/**
 * @author Ana
 */
public class Profile {

	public int idProfile;
	public String nickname;
	public String image;

	public Profile(int idProfile, String nickname, String image) {
		super();
		this.idProfile = idProfile;
		this.nickname = nickname;
		this.image = image;
	}

	public Profile(String nickname, String image) {
		super();
		this.nickname = nickname;
		this.image = image;
	}

	public int getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(int idProfile) {
		this.idProfile = idProfile;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Profile [idProfile=" + idProfile + ", nickname=" + nickname + ", image=" + image + "]";
	}
}

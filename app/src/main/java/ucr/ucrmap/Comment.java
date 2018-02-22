package ucr.ucrmap;

/**
 * Created by Richard on 10/7/2017.
 */

public class Comment
{
    String commentAuthorData;
    String commentDateData;
    String commentBodyData;

    public Comment(String commentAuthorData, String commentDateData, String commentBodyData) {
        this.commentAuthorData = commentAuthorData;
        this.commentDateData = commentDateData;
        this.commentBodyData = commentBodyData;
    }

    public String getCommentAuthorData() {
        return commentAuthorData;
    }

    public void setCommentAuthorData(String commentAuthorData) {
        this.commentAuthorData = commentAuthorData;
    }

    public String getCommentDateData() {
        return commentDateData;
    }

    public void setCommentDateData(String commentDateData) {
        this.commentDateData = commentDateData;
    }

    public String getCommentBodyData() {
        return commentBodyData;
    }

    public void setCommentBodyData(String commentBodyData) {
        this.commentBodyData = commentBodyData;
    }
}

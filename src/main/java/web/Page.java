package web;

public class Page {

    private String url;
    private boolean isRedirect;

    public Page(String url, boolean isRedirect) {
        this.url = url;
        this.isRedirect = isRedirect;
    }

    public Page(String url) {

        this.url = url;

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }
}

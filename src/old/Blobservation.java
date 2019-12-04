package old;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.management.InvalidAttributeValueException;
import javax.management.RuntimeErrorException;



public class Blobservation {
	

	private int width;
	private int height;
	private BlobBoard board;
	
	public Blobservation(int h, int w) {
		board = new BlobBoard(w,h);
		this.width = w;
		this.height = h;
	}
	
	public Blobservation(int w) {
		//create board
		board = new BlobBoard(w,w);
		this.width = w;
		this.height = w;
		
	}
	
	public void populate(List<Map<String,Integer>> generation){
	//populate board
		for(Map<String,Integer> blobDetails:generation) {
			if(blobDetails.get("x") == null||
					blobDetails.get("y") == null||
					blobDetails.get("size") == null) {
				System.out.println("invalid argument: " + blobDetails.toString());
				throw new RuntimeException();
			}
		}
		board.populate(generation);
	}
	
	public void move() {
		//move blobs
		move(1);
	}
	
	public void move(int x) {
		if(x<=0) {
			throw new RuntimeException();
		}
		for(int i =0; i<x;i++) {
			board.processMove();
			//System.out.println(board.printStateString() + "\n");
		}
	}
	
	public List<List<Integer>> printState(){
		//it has to return a list not an array...
		return board.printState();
	}
	
	public String printStateString() {
		return board.printStateString();
	}
	
	public static class Blob{
		protected int size;
		protected int x;
		protected int y;
		
		public NextMove nextMove;
		
		
		public Blob( int x, int y, int size) {
			this.x = x;
			this.y = y;
			this.size = size;
		}
		
		public void setNextMove(Blob blob) {
			if(this.x == blob.x && this.y == blob.y) {
				System.out.println("targetm is same square");
			}
			
			if(blob.x == this.x) {
				//y move
				this.nextMove = new NextMove(x, y + (blob.y - this.y)/Math.abs(this.y-blob.y));
			}
			else if(blob.y == this.y) {
				//x move
				this.nextMove = new NextMove(x+(blob.x - this.x)/Math.abs(this.x-blob.x), y);

			}
			else {
				//x,y move
				this.nextMove = new NextMove(x+(blob.x - this.x)/Math.abs(this.x-blob.x), y+(blob.y - this.y)/Math.abs(this.y-blob.y));
			}
		}
		
		@Override
		public boolean equals(Object o) {
			if(o instanceof Blob) {
				return this.equals((Blob) o);
			}
			return false;
		}
		
		public boolean equals(Blob o) {
			if(o.x == this.x && o.y == this.y && o.size == this.size) {
				return true;
			}
			return false;
		}
	}
	
	public static class BlobBoard{
		/**
		 * Holds an array of BoardPosition objects in an array representing the board
		 */
		
		private int width;
		private int height;
		BoardPosition[][] positions;
		
		private int smallest;
		
		
		
		public BlobBoard(int width, int height) {
			this.width = width;
			this.height = height;
			
			this.positions = createNewBoard(width,height);
		}
		
		public BoardPosition[][] createNewBoard(int width, int height) {
			BoardPosition[][] newPositions = new BoardPosition[width][height];
			for(int x =0;x<height;x++) {
				for(int y =0; y<width;y++) {
					newPositions[x][y] = new BoardPosition(x,y);
				}
			}
			return newPositions;
		}

		public void processMove() {
			/**
			 * ID smallest blob
			 * for each blob in the board that's not the smallest
			 * 	1. Index valid targets with their distances
			 * 	2. Select those closest
			 *  3. apply rules to select final target
			 *  4. determine moves and save to blob
			 *  
			 * then execute moves
			 * for each board position
			 * 	if blobs have a next move (ie if nextMove!=null)
			 * 		add blob to position of next move values
			 * 		remove blob from previous position
			 * 		remove nextMove from blob
			 * 
			 * then execute fuse
			 * 	for each board position call fuse()
			 * 		
			 *  
			 * 	EDGE CASES:
			 * all blobs same size
			 * only 1 blob
			 * no blobs
			 */
			fuseBlobs();
			smallest = getSmallestSize();
			List<Blob> blobsOnBoard =  getListOfBlobs();
			
			
			
			for(BoardPosition[] row: positions) {
				for(BoardPosition bp: row) {
					if(!bp.isEmpty()&&
							bp.getBlobSize()>smallest) {
						findNextMove(bp.getBlob(), blobsOnBoard);
					}
				}
			}
			
			BoardPosition[][] newPositions = createNewBoard(width, height);
			for(int i = 0; i<width; i++) {
				for(int j = 0;j<height; j++) {
					BoardPosition bp = positions[i][j];
					if(!bp.isEmpty()) {
						if(bp.getBlob().nextMove ==null) {
							newPositions[i][j].add(bp.getBlob());;
						}
						else {
							Blob tempBlob = bp.getBlob();
							int newX = tempBlob.nextMove.x;
							int newY =tempBlob.nextMove.y;
							//System.out.println(String.format("Blob moved from (%d, %d) to (%d, %d)", tempBlob.x, tempBlob.y, newX, newY));
							tempBlob.x = newX;
							tempBlob.y = newY;
							newPositions[newX][newY].add(tempBlob);
							tempBlob.nextMove =null;
						}
					}
				}
			}
			positions = newPositions;
			fuseBlobs();
		}
		
		public void fuseBlobs() {
			for(BoardPosition[] row: positions) {
				for(BoardPosition bp: row) {
					bp.fuse();
				}
			}
		}

		public static List<Blob> getValidTargets(List<Blob> blobsOnBoard, Blob blob){
			int x = blob.x;
			int y = blob.y;
			int size = blob.size;
			List<Blob> validTargets = new ArrayList<>();
			int minDistance = Integer.MAX_VALUE;
			for(Blob nextBlob: blobsOnBoard){
				if(blob.size<size){
					int distance = Math.max(Math.abs(nextBlob.x-x), Math.abs(nextBlob.y - y));
					if(distance== minDistance){
						validTargets.add(nextBlob);
					}
					else if(distance<minDistance){
						validTargets.clear();
						validTargets.add(nextBlob);
					}
				}
			}
			return validTargets;
		}

		//TODO test
		public static void findNextMove(Blob blob, List<Blob> blobsOnBoard) {
			int size = blob.size;
//			Map<Integer, List<Blob>> validTargets = new HashMap<>();
			List<Blob> smallestDistanceTargets = getValidTargets(blobsOnBoard,blob);
//			for(Blob nextBlob: blobsOnBoard) {
//				if(nextBlob.size<size) {
//					int distance = Math.max(Math.abs(nextBlob.x-x), Math.abs(nextBlob.y - y));
//					if(validTargets.get(distance) == null){
//						validTargets.put(distance, new ArrayList<>());
//					}
//					validTargets.get(distance).add(nextBlob);
//				}
//			}
//			//handle no targets
//			if(validTargets.isEmpty()) {
//				return;	//no targets found
//			}

//			List<Blob> smallestDistanceTargets = validTargets.get(validTargets.keySet().stream()
//					.reduce(Integer.MAX_VALUE,Math::min));

//			List<Blob> smallestDistanceTargets = validTargets.get(Collections.min(validTargets.keySet()));

			//no targets
			if(smallestDistanceTargets.size()  == 0){return;}
			//only one target
			if(smallestDistanceTargets.size() ==1) {
				blob.setNextMove(smallestDistanceTargets.get(0));
			}
			//TODO implement get largetstSizedtarget			List<Blob> largetSizeTargets = getLargestSizeTargets(List<Blo;>)
//			for(Blob smallestDistanceBlob: smallestDistanceTargets) {
//				int currBlobSize = smallestDistanceBlob.size;
//				if(largestSizeTargets.get(currBlobSize) == null) {
//					largestSizeTargets.put(currBlobSize, new ArrayList<>());
//				}
//				largestSizeTargets.get(currBlobSize).add(smallestDistanceBlob);
//			}
//			List<Blob> largestClosestTargets = largestSizeTargets.get(Collections.max(largestSizeTargets.keySet()));


			//get largest blob of remaining targets
			List<Blob> largestSizeTargets = getLargestSizeTargets(smallestDistanceTargets);

			//only one left
			if(largestSizeTargets.size()==1) {
				blob.setNextMove(largestSizeTargets.get(0));
			}
			
			//rotate clockwise and set that as the next target
			blob.setNextMove(getClockwiseClosest(blob, largestSizeTargets));
		}

		/**
		 * Takes a list of blobs and returns the list of blobs with largest sizes. Returns empty if givne list is empty
		 */
		public static List<Blob> getLargestSizeTargets(List<Blob> blobs){
			if(blobs.isEmpty()){return new ArrayList<Blob>();}

			Integer largestBlobSize = blobs.stream()
					.mapToInt(a -> a.size)
					.max().orElse(0);

			if(largestBlobSize==0){System.out.println("Error - largest blob size of 0 for getLargestBlob list");}

			return blobs.stream()
					.filter(a -> a.size==largestBlobSize)
					.collect(Collectors.toList());
		}
		
		//TODO test	
		public static Blob getClockwiseClosest(Blob sourceBlob, List<Blob> targetBlobs) {
			Map<Double, Blob> blobAngles = new HashMap<>();
			for(Blob blob: targetBlobs) {
				double angle = 0;
				double yDiff = blob.y - sourceBlob.y;
				double xDiff = blob.x - sourceBlob.x;
				
				//check for on axis targets
				if(xDiff == 0) {
					angle = Math.PI/2 + Math.PI *(1-(blob.y -  sourceBlob.y)/(Math.abs(blob.y- sourceBlob.y)));
					
				}
				else if(yDiff == 0) {
					angle = Math.PI *((blob.x -  sourceBlob.x)/(Math.abs(blob.x- sourceBlob.x))+1)/2;
				}
				else {
					String quadrantID = Integer.toString((blob.x -  sourceBlob.x)/(Math.abs(blob.x- sourceBlob.x)))+
							Integer.toString((blob.y -  sourceBlob.y)/(Math.abs(blob.y- sourceBlob.y)));
					switch(quadrantID){
						case "11":
							angle = (double)Math.PI/2 + Math.atan(((double)Math.abs(xDiff/yDiff)));	//checked
							break;
						case "1-1":
							angle = (double)Math.PI + Math.atan(((double)Math.abs(yDiff/xDiff))); //checked

							break;
						case "-1-1":
//							angle = (double)Math.PI*3/2 + Math.atan(((double)Math.abs(xDiff/yDiff)));
							angle = (double)Math.PI*3/2 + Math.atan(((double)Math.abs(xDiff/yDiff))); //Checked
							break;
						case "-11":
							angle = Math.atan(((double)Math.abs(yDiff/xDiff))); //checked
							break;
					}
				}
				
				blobAngles.put(angle,blob);
			}
			return blobAngles.get(Collections.min(blobAngles.keySet()));
		}
		
		public List<Blob> getListOfBlobs(){
			List<Blob> blobsOnBoard = new ArrayList<>();
			for(BoardPosition[] row: positions) {
				for(BoardPosition bp: row) {
					if(!bp.isEmpty()) {
						blobsOnBoard.add(bp.getBlob());
					}
				}
			}
			return blobsOnBoard;
		}
		
		public int getSmallestSize() {
			int smallest = Integer.MAX_VALUE;
			for(BoardPosition[] boardRow:positions) {
				for(BoardPosition posn: boardRow) {
					if(!posn.isEmpty()&&
							posn.getBlobSize()<smallest) {
						smallest = posn.getBlobSize();
					}
				}
			}
			return smallest;
		}
		
		public void populate(List<Map<String,Integer>> generation) {
			for(Map<String,Integer> blob: generation) {
				Blob tempBlob = new Blob(
						blob.get("x"), 
						blob.get("y"), 
						blob.get("size")
						);
				positions[tempBlob.x][tempBlob.y].add(tempBlob);
			}
			fuseBlobs();
		}
		
		public List<List<Integer>> printState(){
			List<List<Integer>> resultList = new ArrayList<>();
			for(int i =0;i<width;i++) {
				for(int j =0; j<height;j++) {
					if(!positions[i][j].isEmpty()){
						int blobX = positions[i][j].getBlob().x;
						int blobY = positions[i][j].getBlob().y;
						int blobSize = positions[i][j].getBlob().size;
						
						resultList.add(Arrays.asList(new Integer[]{blobX, blobY, blobSize}));
					}
				}
			}
			return resultList;
		}
		
		public String printStateString() {
			StringBuilder sb = new StringBuilder();
			for(int i =0;i<width;i++) {
				for(int j =0; j<height;j++) {
					if(!positions[i][j].isEmpty()) {
						sb.append(positions[i][j].printState() + ", ");
					}
				}
			}
			sb.deleteCharAt(sb.length()-1);
			sb.deleteCharAt(sb.length()-1);
			sb.insert(0, "[");
			sb.insert(sb.length()-1, "]");
			return sb.toString();
		}

	}
	
	public static class NextMove{
		
		public final int x;
		public final int y;
		
		public NextMove(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		
		@Override
		public boolean equals(Object o) {
			if(o instanceof NextMove) {
				return this.equals((NextMove)o);
			}
			return false;

		}
		
		public boolean equals(NextMove mv) {
			if(this.x == mv.x && this.y == mv.y) {
				return true;
			}
			return false;
		}
	}
}



class BoardPosition{
	/**
	 * Holds blobs in a given position
	 */
	protected List<Blobservation.Blob> blobs;
	protected int x;
	protected int y;
	
	
	public BoardPosition(int x, int y) {
		this.blobs = new ArrayList<>();
		this.x = x;
		this.y = y;
	}
	public void add(Blobservation.Blob blob) {
		blobs.add(blob);
	}
	
	public void remove(Blobservation.Blob blob) {
		blobs.remove(blob);
	}
	
	public void fuse() {
		if(blobs.size()>1) {
			int newSize = 0;
			for(Blobservation.Blob blob:blobs) {
				newSize += blob.size;
			}
			blobs.clear();
			blobs.add(new Blobservation.Blob(x,y,newSize));
		}
	}
	
	public Blobservation.Blob getBlob() {
		if(blobs.size()>1) {
			System.out.println("getBlobcalled on position with more than one blob, error!");
		}
		return blobs.get(0);
	}
	
	public int getBlobSize() {
		if(blobs.size()>1) {
			System.out.println("blobSize getter called on position with more than one blob, error!");
		}
		return blobs.get(0).size;
	}
	
	public boolean isEmpty() {
		return blobs.isEmpty();
	}
	
	public String printState() {
		String temp = "";
		for(Blobservation.Blob blob:blobs) {
			temp = String.format("[%d, %d, %d]", blob.x, blob.y, blob.size);
		}
		return temp;
	}
}



